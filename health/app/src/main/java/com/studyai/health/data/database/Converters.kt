package com.studyai.health.data.database

import androidx.room.TypeConverter
import com.studyai.health.data.model.HealthRecord
import com.studyai.health.data.model.Recommendation
import com.studyai.health.data.model.StressRecord
import java.util.*

/**
 * Type converters for Room database
 */
class Converters {
    
    // Date converters
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }
    
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
    
    // Enum converters
    @TypeConverter
    fun toRecordType(value: Int): HealthRecord.RecordType {
        return when(value) {
            0 -> HealthRecord.RecordType.HEART_RATE
            1 -> HealthRecord.RecordType.SPO2
            2 -> HealthRecord.RecordType.STRESS
            else -> throw IllegalArgumentException("Invalid RecordType value")
        }
    }
    
    @TypeConverter
    fun fromRecordType(type: HealthRecord.RecordType): Int {
        return when(type) {
            HealthRecord.RecordType.HEART_RATE -> 0
            HealthRecord.RecordType.SPO2 -> 1
            HealthRecord.RecordType.STRESS -> 2
        }
    }
    
    @TypeConverter
    fun toStressLevel(value: Int): StressRecord.StressLevel {
        return when(value) {
            0 -> StressRecord.StressLevel.LOW
            1 -> StressRecord.StressLevel.MEDIUM
            2 -> StressRecord.StressLevel.HIGH
            else -> throw IllegalArgumentException("Invalid StressLevel value")
        }
    }
    
    @TypeConverter
    fun fromStressLevel(level: StressRecord.StressLevel): Int {
        return when(level) {
            StressRecord.StressLevel.LOW -> 0
            StressRecord.StressLevel.MEDIUM -> 1
            StressRecord.StressLevel.HIGH -> 2
        }
    }
    
    @TypeConverter
    fun toRecommendationType(value: Int): Recommendation.RecommendationType {
        return when(value) {
            0 -> Recommendation.RecommendationType.EXERCISE
            1 -> Recommendation.RecommendationType.DIET
            2 -> Recommendation.RecommendationType.RELAXATION
            3 -> Recommendation.RecommendationType.GENERAL
            else -> throw IllegalArgumentException("Invalid RecommendationType value")
        }
    }
    
    @TypeConverter
    fun fromRecommendationType(type: Recommendation.RecommendationType): Int {
        return when(type) {
            Recommendation.RecommendationType.EXERCISE -> 0
            Recommendation.RecommendationType.DIET -> 1
            Recommendation.RecommendationType.RELAXATION -> 2
            Recommendation.RecommendationType.GENERAL -> 3
        }
    }
} 