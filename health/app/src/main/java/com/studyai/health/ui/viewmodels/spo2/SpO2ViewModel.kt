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
    private val requiredReadings = 10 // Số lượng đọc cần thiết để có kết quả đáng tin cậy
    private val confidenceThreshold = 0.7 // Ngưỡng tin cậy tối thiểu
    
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
        // Only accept readings above our confidence threshold
        if (confidence >= confidenceThreshold) {
            readings.add(reading)
            confidences.add(confidence)
            
            // Update the current reading display
            val currentValue = reading.roundToInt()
            uiState = uiState.copy(
                currentReading = "$currentValue%"
            )
        }
    }
    
    private fun finalizeReading() {
        // Filter readings by confidence and calculate the result
        val validReadings = readings.zip(confidences)
            .filter { (_, confidence) -> confidence >= confidenceThreshold }
            .map { (reading, _) -> reading }
        
        if (validReadings.isEmpty()) {
            onMeasurementError("Could not get accurate readings. Please try again in better lighting.")
            return
        }
        
        // Calculate the average SpO2 value from valid readings
        val averageSpO2 = validReadings.average().roundToInt().coerceIn(70, 100)
        val averageConfidence = confidences.average()
        
        uiState = uiState.copy(
            state = MeasurementState.RESULT,
            spO2Result = averageSpO2,
            measurementProgress = 1f,
            confidenceLevel = averageConfidence
        )
    }
    
    fun onMeasurementError(message: String) {
        measurementJob?.cancel()
        uiState = uiState.copy(
            state = MeasurementState.ERROR,
            errorMessage = message
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