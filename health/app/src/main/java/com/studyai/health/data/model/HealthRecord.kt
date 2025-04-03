package com.studyai.health.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Base entity for health records
 */
@Entity(tableName = "health_records")
data class HealthRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val type: RecordType,
    val note: String? = null
) {
    enum class RecordType {
        HEART_RATE,
        SPO2,
        STRESS
    }
}

/**
 * Heart rate measurement record
 */
@Entity(tableName = "heart_rate_records")
data class HeartRateRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val healthRecordId: Int,
    val bpm: Int,
    val accuracy: Float, // 0.0 to 1.0 indicating measurement accuracy
    val duration: Int // Measurement duration in seconds
)

/**
 * SpO2 (oxygen saturation) measurement record
 */
@Entity(tableName = "spo2_records")
data class SpO2Record(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val healthRecordId: Int,
    val percentage: Int, // 0-100%
    val accuracy: Float, // 0.0 to 1.0 indicating measurement accuracy
    val duration: Int // Measurement duration in seconds
)

/**
 * Stress level record
 */
@Entity(tableName = "stress_records")
data class StressRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val healthRecordId: Int,
    val level: StressLevel,
    val hrvScore: Float? = null // Heart Rate Variability score if available
) {
    enum class StressLevel {
        LOW,
        MEDIUM,
        HIGH
    }
}

/**
 * Represents a recommendation given based on health data
 */
@Entity(tableName = "recommendations")
data class Recommendation(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val relatedHealthRecordId: Int? = null,
    val title: String,
    val description: String,
    val category: RecommendationType,
    val isFollowed: Boolean = false
) {
    enum class RecommendationType {
        EXERCISE,
        DIET,
        RELAXATION,
        GENERAL
    }
} 