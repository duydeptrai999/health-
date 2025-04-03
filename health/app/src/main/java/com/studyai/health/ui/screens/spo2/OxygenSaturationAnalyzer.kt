package com.studyai.health.ui.screens.spo2

import android.content.Context
import android.graphics.ImageFormat
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import java.nio.ByteBuffer
import kotlin.math.abs
import kotlin.math.ln

/**
 * Analyzer for processing camera frames to calculate SpO2 values.
 * 
 * This implementation uses the principle of photoplethysmography (PPG) to analyze
 * the red and green channels from the camera image to estimate SpO2 levels.
 * 
 * @param context Application context
 * @param onSpO2Reading Callback for SpO2 reading updates
 * @param onError Callback for error handling
 */
class OxygenSaturationAnalyzer(
    private val context: Context,
    private val onSpO2Reading: (reading: Double, confidence: Double) -> Unit,
    private val onError: (message: String) -> Unit
) : ImageAnalysis.Analyzer {

    private val TAG = "OxygenSaturationAnalyzer"
    
    // Configuration
    private val windowSize = 100 // Number of frames to analyze
    private val minimumConfidence = 0.7 // Minimum confidence level for a valid reading
    
    // Data buffers
    private val redValues = ArrayList<Double>(windowSize)
    private val greenValues = ArrayList<Double>(windowSize)
    
    // Calibration factors
    private var calibrationFactor = 1.0
    
    // State tracking
    private var frameCount = 0
    private var lastFrameTime = 0L
    private var isCalibrated = false
    private var isRunning = false
    
    /**
     * Start the measurement process
     */
    fun startMeasurement() {
        reset()
        isRunning = true
        Log.d(TAG, "SpO2 measurement started")
    }
    
    /**
     * Stop the measurement process
     */
    fun stopMeasurement() {
        isRunning = false
        Log.d(TAG, "SpO2 measurement stopped")
    }
    
    /**
     * Process each image frame to extract red and green channel data.
     */
    override fun analyze(image: ImageProxy) {
        try {
            // Skip processing if not running
            if (!isRunning) {
                image.close()
                return
            }
            
            // Check if this is a supported format
            if (image.format != ImageFormat.YUV_420_888) {
                onError("Unsupported image format: ${image.format}")
                image.close()
                return
            }
            
            // Process the image
            processImage(image)
            
            // Calculate fps for debugging
            val currentTime = System.currentTimeMillis()
            if (lastFrameTime > 0) {
                val fps = 1000.0 / (currentTime - lastFrameTime)
                Log.d(TAG, "Processing at $fps fps")
            }
            lastFrameTime = currentTime

        } catch (e: Exception) {
            Log.e(TAG, "Error analyzing image", e)
            onError("Error analyzing image: ${e.message}")
        } finally {
            image.close()
        }
    }
    
    /**
     * Process the image to extract color channel data.
     */
    private fun processImage(image: ImageProxy) {
        // Get the image planes
        val planes = image.planes
        
        // We only need the Y plane for red detection and UV planes for green
        val yBuffer = planes[0].buffer
        
        // Calculate the average brightness for the center region
        val centerRedValue = calculateAverageRed(yBuffer, image.width, image.height)
        val centerGreenValue = calculateAverageGreen(planes[1].buffer, planes[2].buffer, image.width, image.height)
        
        // Add values to buffers
        redValues.add(centerRedValue)
        greenValues.add(centerGreenValue)
        
        // Remove oldest values if buffer is full
        if (redValues.size > windowSize) {
            redValues.removeAt(0)
            greenValues.removeAt(0)
        }
        
        frameCount++
        
        // Process data after collecting enough frames
        if (redValues.size >= windowSize) {
            calculateSpO2()
        }
    }
    
    /**
     * Calculate average red value from Y plane.
     */
    private fun calculateAverageRed(buffer: ByteBuffer, width: Int, height: Int): Double {
        // We'll focus on the center area
        val centerX = width / 2
        val centerY = height / 2
        val regionSize = minOf(width, height) / 3
        
        var total = 0.0
        var count = 0
        
        // Get a copy of the buffer to avoid position changes
        val yArray = ByteArray(buffer.remaining())
        buffer.get(yArray)
        buffer.rewind()
        
        for (y in (centerY - regionSize/2)..(centerY + regionSize/2)) {
            for (x in (centerX - regionSize/2)..(centerX + regionSize/2)) {
                val index = y * width + x
                if (index < yArray.size) {
                    // Red value is stronger in Y channel
                    total += (yArray[index].toInt() and 0xFF).toDouble()
                    count++
                }
            }
        }
        
        return if (count > 0) total / count else 0.0
    }
    
    /**
     * Calculate average green value from U and V planes.
     */
    private fun calculateAverageGreen(uBuffer: ByteBuffer, vBuffer: ByteBuffer, width: Int, height: Int): Double {
        // Green is derived from a combination of Y, U and V
        // For simplicity, we'll use V-U difference as an approximation for green intensity
        
        val uArray = ByteArray(uBuffer.remaining())
        val vArray = ByteArray(vBuffer.remaining())
        
        uBuffer.get(uArray)
        vBuffer.get(vArray)
        
        uBuffer.rewind()
        vBuffer.rewind()
        
        // UV planes are typically half the resolution of Y
        val uvWidth = width / 2
        val uvHeight = height / 2
        
        val centerX = uvWidth / 2
        val centerY = uvHeight / 2
        val regionSize = minOf(uvWidth, uvHeight) / 3
        
        var total = 0.0
        var count = 0
        
        for (y in (centerY - regionSize/2)..(centerY + regionSize/2)) {
            for (x in (centerX - regionSize/2)..(centerX + regionSize/2)) {
                val index = y * uvWidth + x
                if (index < uArray.size && index < vArray.size) {
                    // Approximate green using U-V difference
                    val u = uArray[index].toInt() and 0xFF
                    val v = vArray[index].toInt() and 0xFF
                    
                    // Higher U - lower V indicates more green
                    val greenApprox = u - v + 128 // Add 128 to avoid negative
                    total += greenApprox
                    count++
                }
            }
        }
        
        return if (count > 0) total / count else 0.0
    }
    
    /**
     * Calculate SpO2 from collected red and green values.
     */
    private fun calculateSpO2() {
        try {
            // Check if we have enough data
            if (redValues.size < windowSize || greenValues.size < windowSize) {
                return
            }
            
            // Calculate AC and DC components for red
            val redAC = calculateAC(redValues)
            val redDC = calculateDC(redValues)
            
            // Calculate AC and DC components for green (instead of IR in medical devices)
            val greenAC = calculateAC(greenValues)
            val greenDC = calculateDC(greenValues)
            
            // Avoid division by zero
            if (redDC <= 0 || greenDC <= 0 || greenAC <= 0) {
                return
            }
            
            // Calculate R value (ratio of ratios)
            val redRatio = redAC / redDC
            val greenRatio = greenAC / greenDC
            val r = redRatio / greenRatio
            
            // Calculate SpO2 using empirical formula
            // SpO2 = 110 - 25 * R (simplified approximation)
            var spO2 = (110 - 25 * r) * calibrationFactor
            
            // Clamp to physiological range
            spO2 = spO2.coerceIn(70.0, 100.0)
            
            // Calculate confidence based on signal quality
            val confidence = calculateConfidence(redValues, greenValues)
            
            // Only report if confidence is high enough
            if (confidence >= minimumConfidence) {
                onSpO2Reading(spO2, confidence)
            }
            
        } catch (e: Exception) {
            Log.e(TAG, "Error calculating SpO2", e)
            onError("Error calculating SpO2: ${e.message}")
        }
    }
    
    /**
     * Calculate AC component (pulsatile) from signal.
     */
    private fun calculateAC(values: List<Double>): Double {
        if (values.isEmpty()) return 0.0
        
        // Apply bandpass filter to isolate 0.5-4Hz components (typical heart rate frequencies)
        // Simplified implementation - peak-to-peak difference
        val max = values.maxOrNull() ?: 0.0
        val min = values.minOrNull() ?: 0.0
        return max - min
    }
    
    /**
     * Calculate DC component (non-pulsatile) from signal.
     */
    private fun calculateDC(values: List<Double>): Double {
        if (values.isEmpty()) return 0.0
        return values.average()
    }
    
    /**
     * Calculate confidence level of the measurement.
     */
    private fun calculateConfidence(redValues: List<Double>, greenValues: List<Double>): Double {
        // Simplified confidence calculation based on:
        // 1. Signal variation (higher is better)
        // 2. Correlation between red and green signals (should be high but negative)
        
        if (redValues.size < 10 || greenValues.size < 10) return 0.0
        
        // Calculate normalized standard deviation for red
        val redMean = redValues.average()
        val redStd = calculateStandardDeviation(redValues)
        val normalizedRedStd = if (redMean > 0) redStd / redMean else 0.0
        
        // Calculate normalized standard deviation for green
        val greenMean = greenValues.average()
        val greenStd = calculateStandardDeviation(greenValues)
        val normalizedGreenStd = if (greenMean > 0) greenStd / greenMean else 0.0
        
        // Calculate correlation between signals
        val correlation = calculateCorrelation(redValues, greenValues)
        
        // Combine factors for overall confidence
        // - We want good variation in both signals (pulsatile component)
        // - We want negative correlation (as red absorption increases, green should decrease)
        
        val variationConfidence = minOf(normalizedRedStd, normalizedGreenStd) * 10 // Scale up
        val correlationConfidence = abs(correlation) * (if (correlation < 0) 1.0 else 0.5)
        
        return (variationConfidence * 0.7 + correlationConfidence * 0.3).coerceIn(0.0, 1.0)
    }
    
    /**
     * Calculate standard deviation of a list of values.
     */
    private fun calculateStandardDeviation(values: List<Double>): Double {
        if (values.size <= 1) return 0.0
        
        val mean = values.average()
        val variance = values.sumOf { (it - mean) * (it - mean) } / (values.size - 1)
        return kotlin.math.sqrt(variance)
    }
    
    /**
     * Calculate correlation coefficient between two signals.
     */
    private fun calculateCorrelation(xValues: List<Double>, yValues: List<Double>): Double {
        if (xValues.size != yValues.size || xValues.isEmpty()) return 0.0
        
        val n = xValues.size
        val xMean = xValues.average()
        val yMean = yValues.average()
        
        var numerator = 0.0
        var xDenominator = 0.0
        var yDenominator = 0.0
        
        for (i in 0 until n) {
            val xDiff = xValues[i] - xMean
            val yDiff = yValues[i] - yMean
            numerator += xDiff * yDiff
            xDenominator += xDiff * xDiff
            yDenominator += yDiff * yDiff
        }
        
        if (xDenominator <= 0 || yDenominator <= 0) return 0.0
        
        return numerator / (kotlin.math.sqrt(xDenominator) * kotlin.math.sqrt(yDenominator))
    }
    
    /**
     * Set calibration factor based on reference device.
     */
    fun calibrate(referenceSpO2: Double) {
        // Only accept valid reference values
        if (referenceSpO2 < 70 || referenceSpO2 > 100) {
            Log.e(TAG, "Invalid reference SpO2 value for calibration: $referenceSpO2")
            return
        }
        
        // Calculate temporary SpO2 without calibration
        calculateSpO2()
        
        // Update calibration factor
        // If we've calculated a recent SpO2 value, use it for calibration
        if (lastSpO2 > 0) {
            calibrationFactor = referenceSpO2 / lastSpO2
            isCalibrated = true
            Log.d(TAG, "Calibrated with factor: $calibrationFactor")
        }
    }
    
    /**
     * Reset the analyzer.
     */
    fun reset() {
        redValues.clear()
        greenValues.clear()
        frameCount = 0
    }
    
    companion object {
        // Last calculated SpO2 value for calibration
        private var lastSpO2 = 0.0
    }
} 