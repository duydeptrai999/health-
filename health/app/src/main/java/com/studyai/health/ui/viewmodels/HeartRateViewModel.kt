package com.studyai.health.ui.viewmodels

import android.content.Context
import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyai.health.HealthApplication
import com.studyai.health.data.model.StressRecord
import com.studyai.health.data.repository.HealthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * ViewModel for heart rate measurement functionality
 */
class HeartRateViewModel : ViewModel() {
    
    // State of the measurement process
    enum class MeasurementState {
        IDLE,       // Not measuring
        MEASURING,  // Measurement in progress
        COMPLETED   // Measurement completed
    }
    
    // Repository for health data
    private val healthRepository: HealthRepository by lazy {
        HealthApplication.instance.healthRepository
    }
    
    // Current measurement state
    private val _measurementState = MutableStateFlow(MeasurementState.IDLE)
    val measurementState: StateFlow<MeasurementState> = _measurementState.asStateFlow()
    
    // Current heart rate reading
    private val _heartRate = MutableStateFlow(0)
    val heartRate: StateFlow<Int> = _heartRate.asStateFlow()
    
    // Elapsed time in seconds
    private val _elapsedTime = MutableStateFlow(0)
    val elapsedTime: StateFlow<Int> = _elapsedTime.asStateFlow()
    
    // Timer for the measurement
    private var measurementTimer: CountDownTimer? = null
    
    // Quality of the measurement (0.0 to 1.0)
    private var measurementQuality = 0.0f
    
    /**
     * Start heart rate measurement
     */
    fun startMeasurement(context: Context) {
        // Reset state
        _heartRate.value = 0
        _elapsedTime.value = 0
        measurementQuality = 0.0f
        
        // Change state to measuring
        _measurementState.value = MeasurementState.MEASURING
        
        // In a real application, we would initialize camera and start analyzing frames
        // For this demo, we'll simulate the heart rate measurement with a timer
        
        // Create a timer that runs for 45 seconds
        measurementTimer = object : CountDownTimer(45000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Update elapsed time
                _elapsedTime.value = (45000 - millisUntilFinished).toInt() / 1000
                
                // Simulate heart rate readings
                if (_elapsedTime.value >= 5) {
                    // After 5 seconds, start showing heart rate
                    // In a real app, this would be calculated from camera frames
                    val baseHeartRate = 70 // Average heart rate
                    val variation = Random.nextInt(-3, 4) // Small random variation
                    
                    _heartRate.value = baseHeartRate + variation
                    
                    // Quality increases over time
                    measurementQuality = (_elapsedTime.value / 45.0f).coerceAtMost(0.95f)
                }
                
                // After 30 seconds, we consider it a valid measurement
                if (_elapsedTime.value >= 30) {
                    // In a real app, we would check if the measurement is stable
                    // For this demo, we'll automatically complete after 30 seconds
                    stopMeasurement()
                }
            }
            
            override fun onFinish() {
                // Measurement completed after the full 45 seconds
                _elapsedTime.value = 45
                stopMeasurement()
            }
        }
        
        // Start the timer
        measurementTimer?.start()
    }
    
    /**
     * Stop the ongoing measurement
     */
    fun stopMeasurement() {
        // Stop the timer
        measurementTimer?.cancel()
        measurementTimer = null
        
        // Only save if we have a valid heart rate
        if (_heartRate.value > 0 && _elapsedTime.value >= 10) {
            // Set state to completed
            _measurementState.value = MeasurementState.COMPLETED
        } else {
            // Reset if we don't have enough data
            _measurementState.value = MeasurementState.IDLE
            _heartRate.value = 0
            _elapsedTime.value = 0
        }
    }
    
    /**
     * Save the heart rate result to the database
     */
    fun saveResult() {
        if (_measurementState.value == MeasurementState.COMPLETED && _heartRate.value > 0) {
            viewModelScope.launch {
                // Save heart rate measurement
                val healthRecordId = healthRepository.saveHeartRateMeasurement(
                    bpm = _heartRate.value,
                    accuracy = measurementQuality,
                    duration = _elapsedTime.value
                )
                
                // If heart rate is abnormal, automatically analyze stress level
                if (_heartRate.value < 60 || _heartRate.value > 100) {
                    // Determine stress level based on heart rate
                    val stressLevel = when {
                        _heartRate.value > 120 -> StressRecord.StressLevel.HIGH
                        _heartRate.value > 100 -> StressRecord.StressLevel.MEDIUM
                        _heartRate.value < 50 -> StressRecord.StressLevel.MEDIUM
                        else -> StressRecord.StressLevel.LOW
                    }
                    
                    // Save stress measurement
                    healthRepository.saveStressMeasurement(
                        level = stressLevel,
                        hrvScore = calculateHrvScore(_heartRate.value)
                    )
                }
                
                // Reset state after saving
                _measurementState.value = MeasurementState.IDLE
                _heartRate.value = 0
                _elapsedTime.value = 0
            }
        }
    }
    
    /**
     * Calculate a simulated HRV score based on heart rate
     * In a real app, this would be calculated from the actual heart rate variability
     */
    private fun calculateHrvScore(heartRate: Int): Float {
        // This is a simplified simulation
        // Lower HRV (bad) when heart rate is very high or very low
        return when {
            heartRate > 120 -> 30.0f
            heartRate > 100 -> 50.0f
            heartRate in 60..80 -> 70.0f
            heartRate < 60 -> 45.0f
            else -> 60.0f
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        // Cleanup
        measurementTimer?.cancel()
    }
} 