package com.studyai.health.data.utils

import androidx.room.TypeConverter
import com.studyai.health.data.models.HealthMetric

/**
 * Type converter for converting HealthMetric enum to/from String for Room database storage.
 */
class HealthMetricConverter {
    /**
     * Convert a string name to a HealthMetric enum value.
     * @param value The string name of the enum value
     * @return The corresponding HealthMetric enum value, or null if value is null
     */
    @TypeConverter
    fun fromString(value: String?): HealthMetric? {
        return value?.let { enumValueOf<HealthMetric>(it) }
    }

    /**
     * Convert a HealthMetric enum value to its string name.
     * @param metric The HealthMetric enum value
     * @return The string name of the enum value, or null if metric is null
     */
    @TypeConverter
    fun toString(metric: HealthMetric?): String? {
        return metric?.name
    }
} 