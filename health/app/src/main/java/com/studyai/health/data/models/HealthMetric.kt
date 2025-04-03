package com.studyai.health.data.models

/**
 * Enumeration class representing different health metrics supported by the application.
 */
enum class HealthMetric {
    HEART_RATE,
    BLOOD_PRESSURE,
    SPO2,
    TEMPERATURE,
    SLEEP,
    WEIGHT,
    BMI,
    STEPS,
    WATER_INTAKE;
    
    /**
     * Get the display name of the health metric.
     */
    fun getDisplayName(): String {
        return when (this) {
            HEART_RATE -> "Heart Rate"
            BLOOD_PRESSURE -> "Blood Pressure"
            SPO2 -> "Blood Oxygen"
            TEMPERATURE -> "Body Temperature"
            SLEEP -> "Sleep Duration"
            WEIGHT -> "Weight"
            BMI -> "BMI"
            STEPS -> "Steps"
            WATER_INTAKE -> "Water Intake"
        }
    }
    
    /**
     * Get the unit of measurement for the health metric.
     */
    fun getUnit(): String {
        return when (this) {
            HEART_RATE -> "bpm"
            BLOOD_PRESSURE -> "mmHg"
            SPO2 -> "%"
            TEMPERATURE -> "°C"
            SLEEP -> "hours"
            WEIGHT -> "kg"
            BMI -> "kg/m²"
            STEPS -> "steps"
            WATER_INTAKE -> "ml"
        }
    }
    
    /**
     * Get the normal range for the health metric.
     * @return Pair of minimum and maximum normal values
     */
    fun getNormalRange(): Pair<Double, Double> {
        return when (this) {
            HEART_RATE -> Pair(60.0, 100.0)
            BLOOD_PRESSURE -> Pair(90.0, 120.0) // Systolic
            SPO2 -> Pair(95.0, 100.0)
            TEMPERATURE -> Pair(36.1, 37.2)
            SLEEP -> Pair(7.0, 9.0)
            WEIGHT -> Pair(18.5, 24.9) // This is actually BMI range
            BMI -> Pair(18.5, 24.9)
            STEPS -> Pair(7000.0, 10000.0)
            WATER_INTAKE -> Pair(2000.0, 3000.0)
        }
    }
    
    /**
     * Evaluate if a value is within normal range for this metric.
     * @param value The value to evaluate
     * @return true if the value is within normal range, false otherwise
     */
    fun isNormal(value: Double): Boolean {
        val (min, max) = getNormalRange()
        return value in min..max
    }
} 