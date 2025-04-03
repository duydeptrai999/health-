package com.studyai.health.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

/**
 * Entity representing a scheduled reminder
 */
@Entity(tableName = "reminders")
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val message: String,
    val timeHour: Int,
    val timeMinute: Int,
    val type: ReminderType,
    val isRepeating: Boolean = false,
    val repeatDays: Int = 0, // Bitmap of days, bit 0 = Sunday, bit 1 = Monday, etc.
    val intervalMillis: Long = 0, // For custom repeat intervals
    val isEnabled: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
) {
    enum class ReminderType {
        MEASUREMENT,
        WATER,
        EXERCISE,
        MEDICATION,
        CUSTOM
    }
    
    /**
     * Calculate the next time this reminder should trigger
     * 
     * @return timestamp in milliseconds for the next trigger time
     */
    fun calculateNextTriggerTime(): Long {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, timeHour)
            set(Calendar.MINUTE, timeMinute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        
        // If the time has already passed today, schedule for the next occurrence
        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            if (isRepeating && repeatDays > 0) {
                // For repeating reminders with specific days, find the next day
                val currentDayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1 // 0-based
                
                // Start from tomorrow
                var daysToAdd = 1
                var nextDay = (currentDayOfWeek + daysToAdd) % 7
                
                // Find the next enabled day
                while (((1 shl nextDay) and repeatDays) == 0 && daysToAdd < 7) {
                    daysToAdd++
                    nextDay = (currentDayOfWeek + daysToAdd) % 7
                }
                
                calendar.add(Calendar.DAY_OF_YEAR, daysToAdd)
            } else {
                // For non-repeating or daily reminders, schedule for tomorrow
                calendar.add(Calendar.DAY_OF_YEAR, 1)
            }
        } else if (isRepeating && repeatDays > 0) {
            // Check if today is a repeat day
            val currentDayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1 // 0-based
            val isTodayEnabled = ((1 shl currentDayOfWeek) and repeatDays) != 0
            
            if (!isTodayEnabled) {
                // Today is not a repeat day, find the next day
                var daysToAdd = 1
                var nextDay = (currentDayOfWeek + daysToAdd) % 7
                
                // Find the next enabled day
                while (((1 shl nextDay) and repeatDays) == 0 && daysToAdd < 7) {
                    daysToAdd++
                    nextDay = (currentDayOfWeek + daysToAdd) % 7
                }
                
                calendar.add(Calendar.DAY_OF_YEAR, daysToAdd)
            }
        }
        
        return calendar.timeInMillis
    }
} 