package com.studyai.health.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.studyai.health.data.dao.HealthRecordDao
import com.studyai.health.data.models.HealthRecord
import com.studyai.health.data.utils.DateConverter
import com.studyai.health.data.utils.HealthMetricConverter

/**
 * Main database class for the application.
 * Defines the database configuration and serves as the main access point for the connection.
 */
@Database(
    entities = [HealthRecord::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class, HealthMetricConverter::class)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Get the DAO for health records.
     */
    abstract fun healthRecordDao(): HealthRecordDao
    
    companion object {
        // Singleton instance
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        /**
         * Get the database instance, creating it if it doesn't exist.
         * @param context Application context
         * @return Database instance
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "health_app_database"
                )
                .fallbackToDestructiveMigration() // Wipe and rebuild the db on schema changes
                .build()
                
                INSTANCE = instance
                instance
            }
        }
    }
} 