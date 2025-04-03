package com.studyai.health.data.utils

import androidx.room.TypeConverter
import java.util.Date

/**
 * Type converter for converting Date objects to/from Long for Room database storage.
 */
class DateConverter {
    /**
     * Convert a timestamp value to a Date object.
     * @param value The timestamp value in milliseconds since epoch
     * @return The corresponding Date object, or null if value is null
     */
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    /**
     * Convert a Date object to a timestamp value.
     * @param date The Date object to convert
     * @return The timestamp value in milliseconds since epoch, or null if date is null
     */
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
} 