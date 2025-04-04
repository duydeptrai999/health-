package com.studyai.health.ui.viewmodels.spo2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyai.health.data.models.HealthMetric
import com.studyai.health.data.models.HealthRecord
import com.studyai.health.data.repositories.HealthRecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject
import kotlin.math.roundToInt

enum class MeasurementState {
    DISCLAIMER,
    READY,
    MEASURING,
    RESULT,
    ERROR
}

/**
 * UI state for SpO2 measurement screen
 */
data class SpO2UiState(
    val state: MeasurementState = MeasurementState.DISCLAIMER,
    val measurementProgress: Float = 0f,
    val currentReading: String = "--",
    val spO2Result: Int = 0,
    val errorMessage: String = "",
    val isSaved: Boolean = false,
    val confidenceLevel: Double = 0.0
)

/**
 * ViewModel for SpO2 measurement screen
 */
@HiltViewModel
class SpO2ViewModel @Inject constructor(
    private val healthRecordRepository: HealthRecordRepository
) : ViewModel() {

    // UI state
    var uiState by mutableStateOf(SpO2UiState())
        private set
    
    // Job for simulating measurement
    private var measurementJob: Job? = null
    private var readings = mutableListOf<Double>()
    private var confidences = mutableListOf<Double>()
    private val requiredReadings = 8 // Giảm số lượng đọc cần thiết để có kết quả nhanh hơn
    private val confidenceThreshold = 0.5 // Giảm ngưỡng tin cậy xuống để dễ có kết quả hơn
    private var errorCount = 0 // Đếm số lần gặp lỗi để quyết định báo lỗi
    private var lastErrorMessage = "" // Theo dõi thông báo lỗi cuối cùng
    
    /**
     * Called when the disclaimer is accepted
     */
    fun onDisclaimerAccepted() {
        uiState = uiState.copy(state = MeasurementState.READY)
    }
    
    /**
     * Called when camera permission is granted
     */
    fun onCameraPermissionGranted() {
        if (uiState.state == MeasurementState.DISCLAIMER) {
            // Don't change state until disclaimer is accepted
            return
        }
        if (uiState.state != MeasurementState.MEASURING && uiState.state != MeasurementState.RESULT) {
            uiState = uiState.copy(state = MeasurementState.READY)
        }
    }
    
    /**
     * Start the SpO2 measurement
     */
    fun startMeasurement() {
        // Reset collections
        readings.clear()
        confidences.clear()
        
        uiState = uiState.copy(
            state = MeasurementState.MEASURING,
            measurementProgress = 0f,
            currentReading = "--"
        )
        
        measurementJob?.cancel()
        measurementJob = viewModelScope.launch {
            try {
                // Initialize progress counter
                var progress = 0f
                val totalDuration = 20000 // 20 seconds total for measurement
                val updateInterval = 100 // Update UI every 100ms
                
                // Simulate progress over time
                while (progress < 1f) {
                    delay(updateInterval.toLong())
                    progress += updateInterval.toFloat() / totalDuration.toFloat()
                    progress = progress.coerceAtMost(1f)
                    
                    uiState = uiState.copy(measurementProgress = progress)
                    
                    // If we have enough readings and we're at least 5 seconds in (50% confidence)
                    if (readings.size >= requiredReadings && progress >= 0.5f) {
                        // Calculate average confidence
                        val avgConfidence = confidences.average()
                        if (avgConfidence >= confidenceThreshold) {
                            // We have enough confident readings to finish early
                            finalizeReading()
                            return@launch
                        }
                    }
                }
                
                // If we complete the full duration, finalize the reading
                if (readings.isNotEmpty()) {
                    finalizeReading()
                } else {
                    onMeasurementError("No valid readings detected. Please try again.")
                }
            } catch (e: Exception) {
                onMeasurementError("Measurement failed: ${e.message}")
            }
        }
    }
    
    fun onSpO2Reading(reading: Double, confidence: Double) {
        // Chỉ chấp nhận đọc khi vượt qua ngưỡng tin cậy
        if (confidence >= confidenceThreshold) {
            readings.add(reading)
            confidences.add(confidence)
            
            // Cập nhật giá trị hiện tại
            val currentValue = reading.roundToInt()
            uiState = uiState.copy(
                currentReading = "$currentValue%"
            )
            
            // Reset error counter mỗi khi có đọc thành công
            errorCount = 0
        }
    }
    
    fun onMeasurementError(message: String) {
        // Nếu thông báo lỗi giống với thông báo cuối, tăng bộ đếm lỗi
        if (message == lastErrorMessage) {
            errorCount++
        } else {
            // Nếu là lỗi mới, reset bộ đếm và lưu lỗi mới
            errorCount = 1
            lastErrorMessage = message
        }
        
        // Chỉ hiển thị lỗi nếu cùng một lỗi xuất hiện quá nhiều lần
        // hoặc nếu đã đo quá lâu mà không có kết quả
        if (errorCount >= 5 || (uiState.measurementProgress > 0.8f && readings.isEmpty())) {
            measurementJob?.cancel()
            uiState = uiState.copy(
                state = MeasurementState.ERROR,
                errorMessage = message
            )
        }
    }
    
    private fun finalizeReading() {
        // Lọc các đọc theo độ tin cậy và tính kết quả
        val validReadings = readings.zip(confidences)
            .filter { (_, confidence) -> confidence >= confidenceThreshold }
            .map { (reading, _) -> reading }
        
        if (validReadings.isEmpty()) {
            onMeasurementError("Không thể có đọc chính xác. Hãy thử lại với ánh sáng tốt hơn.")
            return
        }
        
        // Tính giá trị SpO2 trung bình từ các đọc hợp lệ
        // Sử dụng trung bình có trọng số với độ tin cậy
        val weightedReadings = validReadings.zip(confidences)
            .filter { (_, confidence) -> confidence >= confidenceThreshold }
        
        val weightedSum = weightedReadings.sumOf { (reading, confidence) -> reading * confidence }
        val weightSum = weightedReadings.sumOf { (_, confidence) -> confidence }
        
        val averageSpO2 = if (weightSum > 0) {
            (weightedSum / weightSum).roundToInt().coerceIn(70, 100)
        } else {
            validReadings.average().roundToInt().coerceIn(70, 100)
        }
        
        val averageConfidence = confidences.average()
        
        uiState = uiState.copy(
            state = MeasurementState.RESULT,
            spO2Result = averageSpO2,
            measurementProgress = 1f,
            confidenceLevel = averageConfidence
        )
    }
    
    /**
     * Save the SpO2 result to the database
     */
    fun saveResult() {
        viewModelScope.launch {
            try {
                if (uiState.state == MeasurementState.RESULT && !uiState.isSaved) {
                    val healthRecord = HealthRecord(
                        timestamp = Date(),
                        metric = HealthMetric.SPO2,
                        value = uiState.spO2Result.toDouble(),
                        confidence = uiState.confidenceLevel
                    )
                    
                    healthRecordRepository.insertHealthRecord(healthRecord)
                    uiState = uiState.copy(isSaved = true)
                }
            } catch (e: Exception) {
                // Handle error saving to database
                uiState = uiState.copy(
                    errorMessage = "Failed to save result: ${e.message}"
                )
            }
        }
    }
    
    /**
     * Reset the measurement to try again
     */
    fun resetMeasurement() {
        uiState = uiState.copy(
            state = MeasurementState.READY,
            measurementProgress = 0f,
            currentReading = "--",
            isSaved = false
        )
    }
    
    override fun onCleared() {
        super.onCleared()
        measurementJob?.cancel()
    }
} 