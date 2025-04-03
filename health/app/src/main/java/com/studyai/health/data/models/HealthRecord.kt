package com.studyai.health.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.studyai.health.data.utils.DateConverter
import com.studyai.health.data.utils.HealthMetricConverter
import java.util.Date

/**
 * Entity representing a health record in the database.
 * Each record contains a single health measurement at a specific time.
 */
@Entity(tableName = "health_records")
@TypeConverters(DateConverter::class, HealthMetricConverter::class)
data class HealthRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    /**
     * The timestamp when the measurement was taken
     */
    val timestamp: Date,
    
    /**
     * The type of health metric (e.g., HEART_RATE, BLOOD_PRESSURE, SPO2)
     */
    val metric: HealthMetric,
    
    /**
     * The measured value
     */
    val value: Double,
    
    /**
     * Optional confidence level for the measurement (0.0 to 1.0)
     * Useful for measurements taken with the camera or other sensors
     */
    val confidence: Double = 1.0,
    
    /**
     * Optional secondary value (e.g., diastolic blood pressure)
     */
    val secondaryValue: Double? = null,
    
    /**
     * Optional additional notes about the measurement
     */
    val notes: String? = null,
    
    /**
     * Flag indicating if the record was synchronized with the cloud
     */
    val isSync: Boolean = false
) {
    /**
     * Get the formatted value with unit for display
     */
    fun getFormattedValue(): String {
        return when (metric) {
            HealthMetric.BLOOD_PRESSURE -> {
                if (secondaryValue != null) {
                    "${value.toInt()}/${secondaryValue.toInt()} ${metric.getUnit()}"
                } else {
                    "${value.toInt()} ${metric.getUnit()}"
                }
            }
            HealthMetric.SPO2, HealthMetric.HEART_RATE -> "${value.toInt()} ${metric.getUnit()}"
            HealthMetric.TEMPERATURE -> String.format("%.1f ${metric.getUnit()}", value)
            HealthMetric.WEIGHT, HealthMetric.BMI -> String.format("%.1f ${metric.getUnit()}", value)
            else -> "${value} ${metric.getUnit()}"
        }
    }
    
    /**
     * Determine if the recorded value is within normal range
     */
    fun isWithinNormalRange(): Boolean {
        return metric.isNormal(value)
    }
} 