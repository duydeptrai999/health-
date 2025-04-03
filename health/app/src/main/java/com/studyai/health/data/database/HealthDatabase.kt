package com.studyai.health.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.studyai.health.data.model.*

/**
 * Main Room database for the application
 */
@Database(
    entities = [
        HealthRecord::class,
        HeartRateRecord::class,
        SpO2Record::class,
        StressRecord::class,
        Recommendation::class,
        Reminder::class,
        AiConversation::class,
        AiMessage::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class HealthDatabase : RoomDatabase() {
    
    /**
     * DAO for health records
     */
    abstract fun healthDao(): HealthDao
    
    /**
     * DAO for reminders
     */
    abstract fun reminderDao(): ReminderDao
    
    /**
     * DAO for AI conversations
     */
    abstract fun aiConversationDao(): AiConversationDao
} 