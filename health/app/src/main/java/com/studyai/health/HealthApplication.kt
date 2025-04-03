package com.studyai.health

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.room.Room
import com.studyai.health.data.database.HealthDatabase
import com.studyai.health.data.network.ApiService
import com.studyai.health.data.repository.HealthRepository
import com.studyai.health.data.repository.ReminderRepository
import com.studyai.health.util.NotificationHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class HealthApplication : Application() {

    // Database
    lateinit var database: HealthDatabase
        private set
    
    // Repositories
    lateinit var healthRepository: HealthRepository
        private set
    
    lateinit var reminderRepository: ReminderRepository
        private set
    
    // API Service
    lateinit var apiService: ApiService
        private set
    
    override fun onCreate() {
        super.onCreate()
        
        instance = this
        
        // Initialize Database
        database = Room.databaseBuilder(
            applicationContext,
            HealthDatabase::class.java,
            "health_database"
        ).fallbackToDestructiveMigration()
            .build()
        
        // Initialize Network
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
        
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.example.com/") // Replace with actual API URL
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        
        apiService = retrofit.create(ApiService::class.java)
        
        // Initialize Repositories
        healthRepository = HealthRepository(database.healthDao(), apiService)
        reminderRepository = ReminderRepository(database.reminderDao())
        
        // Create notification channels
        createNotificationChannels()
    }
    
    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            
            // Reminder Channel
            val reminderChannel = NotificationChannel(
                NotificationHelper.CHANNEL_REMINDERS,
                "Health Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for health measurements and activities"
                enableLights(true)
                enableVibration(true)
            }
            
            // Health Alert Channel
            val alertChannel = NotificationChannel(
                NotificationHelper.CHANNEL_ALERTS,
                "Health Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Important alerts about your health metrics"
                enableLights(true)
                enableVibration(true)
            }
            
            notificationManager.createNotificationChannel(reminderChannel)
            notificationManager.createNotificationChannel(alertChannel)
        }
    }
    
    companion object {
        lateinit var instance: HealthApplication
            private set
    }
} 