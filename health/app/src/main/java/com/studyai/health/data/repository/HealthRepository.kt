package com.studyai.health.data.repository

import androidx.lifecycle.LiveData
import com.studyai.health.data.database.HealthDao
import com.studyai.health.data.model.*
import com.studyai.health.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

/**
 * Repository for health data
 */
class HealthRepository(
    private val healthDao: HealthDao,
    private val apiService: ApiService
) {
    
    // Health records
    fun getAllHealthRecords() = healthDao.getAllHealthRecords()
    
    fun getHealthRecordsByType(type: HealthRecord.RecordType) = healthDao.getHealthRecordsByType(type)
    
    fun getHealthRecordsByTimeRange(startTime: Long, endTime: Long) = 
        healthDao.getHealthRecordsByTimeRange(startTime, endTime)
    
    fun getHealthRecordsByTypeAndTimeRange(
        type: HealthRecord.RecordType,
        startTime: Long,
        endTime: Long
    ) = healthDao.getHealthRecordsByTypeAndTimeRange(type, startTime, endTime)
    
    suspend fun getHealthRecordById(id: Int) = 
        withContext(Dispatchers.IO) {
            healthDao.getHealthRecordById(id)
        }
    
    // Heart rate
    suspend fun saveHeartRateMeasurement(bpm: Int, accuracy: Float, duration: Int, note: String? = null) =
        withContext(Dispatchers.IO) {
            healthDao.saveHeartRateMeasurement(bpm, accuracy, duration, note)
        }
    
    suspend fun getHeartRateRecordByHealthId(healthRecordId: Int) =
        withContext(Dispatchers.IO) {
            healthDao.getHeartRateRecordByHealthId(healthRecordId)
        }
    
    fun getHeartRateRecordsByTimeRange(startTime: Long, endTime: Long) =
        healthDao.getHeartRateRecordsByTimeRange(startTime, endTime)
    
    // SpO2
    suspend fun saveSpO2Measurement(percentage: Int, accuracy: Float, duration: Int, note: String? = null) =
        withContext(Dispatchers.IO) {
            healthDao.saveSpO2Measurement(percentage, accuracy, duration, note)
        }
    
    suspend fun getSpO2RecordByHealthId(healthRecordId: Int) =
        withContext(Dispatchers.IO) {
            healthDao.getSpO2RecordByHealthId(healthRecordId)
        }
    
    fun getSpO2RecordsByTimeRange(startTime: Long, endTime: Long) =
        healthDao.getSpO2RecordsByTimeRange(startTime, endTime)
    
    // Stress
    suspend fun saveStressMeasurement(level: StressRecord.StressLevel, hrvScore: Float? = null, note: String? = null) =
        withContext(Dispatchers.IO) {
            healthDao.saveStressMeasurement(level, hrvScore, note)
        }
    
    suspend fun getStressRecordByHealthId(healthRecordId: Int) =
        withContext(Dispatchers.IO) {
            healthDao.getStressRecordByHealthId(healthRecordId)
        }
    
    fun getStressRecordsByTimeRange(startTime: Long, endTime: Long) =
        healthDao.getStressRecordsByTimeRange(startTime, endTime)
    
    // Recommendations
    fun getAllRecommendations() = healthDao.getAllRecommendations()
    
    fun getRecommendationsForHealthRecord(healthRecordId: Int) =
        healthDao.getRecommendationsForHealthRecord(healthRecordId)
    
    fun getRecommendationsByCategory(category: Recommendation.RecommendationType) =
        healthDao.getRecommendationsByCategory(category)
    
    suspend fun saveRecommendation(recommendation: Recommendation) =
        withContext(Dispatchers.IO) {
            healthDao.insertRecommendation(recommendation)
        }
    
    suspend fun updateRecommendation(recommendation: Recommendation) =
        withContext(Dispatchers.IO) {
            healthDao.updateRecommendation(recommendation)
        }
    
    suspend fun deleteRecommendation(recommendation: Recommendation) =
        withContext(Dispatchers.IO) {
            healthDao.deleteRecommendation(recommendation)
        }
    
    // API interactions
    suspend fun getAiResponse(message: String, includeHealthData: Boolean = true): AiResponse =
        withContext(Dispatchers.IO) {
            val healthData = if (includeHealthData) {
                // Collect recent health data to include in the request
                collectRecentHealthData()
            } else {
                null
            }
            
            apiService.getAiResponse(message, includeHealthData, healthData)
        }
    
    suspend fun getHealthRecommendations(): List<Recommendation> =
        withContext(Dispatchers.IO) {
            val healthData = collectRecentHealthData()
            apiService.getRecommendations(healthData)
        }
    
    // Helper methods
    private suspend fun collectRecentHealthData(): Map<String, Any> {
        // Get data from the last 7 days
        val calendar = Calendar.getInstance()
        val endTime = calendar.timeInMillis
        
        calendar.add(Calendar.DAY_OF_YEAR, -7)
        val startTime = calendar.timeInMillis
        
        // Helper function to get latest record of a type
        fun getLatestRecord(type: HealthRecord.RecordType): HealthRecord? {
            val records = healthDao.getHealthRecordsByTypeAndTimeRange(type, startTime, endTime)
                .value?.sortedByDescending { it.timestamp }
            return records?.firstOrNull()
        }
        
        // Get latest heart rate record
        val latestHeartRateRecord = getLatestRecord(HealthRecord.RecordType.HEART_RATE)
        val heartRate = latestHeartRateRecord?.let {
            healthDao.getHeartRateRecordByHealthId(it.id)?.bpm
        }
        
        // Get latest SpO2 record
        val latestSpO2Record = getLatestRecord(HealthRecord.RecordType.SPO2)
        val spO2 = latestSpO2Record?.let {
            healthDao.getSpO2RecordByHealthId(it.id)?.percentage
        }
        
        // Get latest stress record
        val latestStressRecord = getLatestRecord(HealthRecord.RecordType.STRESS)
        val stressLevel = latestStressRecord?.let {
            val stress = healthDao.getStressRecordByHealthId(it.id)
            stress?.level?.name
        }
        
        // Build data map
        val healthDataMap = mutableMapOf<String, Any>()
        
        heartRate?.let { healthDataMap["heartRate"] = it }
        spO2?.let { healthDataMap["spO2"] = it }
        stressLevel?.let { healthDataMap["stressLevel"] = it }
        
        // Add timestamp for the data
        healthDataMap["timestamp"] = System.currentTimeMillis()
        
        return healthDataMap
    }
} 