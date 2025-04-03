package com.studyai.health.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.studyai.health.data.model.*
import java.util.*

/**
 * Data Access Object for health-related entities
 */
@Dao
interface HealthDao {
    
    // Health record methods
    @Insert
    suspend fun insertHealthRecord(record: HealthRecord): Long
    
    @Update
    suspend fun updateHealthRecord(record: HealthRecord)
    
    @Delete
    suspend fun deleteHealthRecord(record: HealthRecord)
    
    @Query("SELECT * FROM health_records WHERE id = :id")
    suspend fun getHealthRecordById(id: Int): HealthRecord?
    
    @Query("SELECT * FROM health_records ORDER BY timestamp DESC")
    fun getAllHealthRecords(): LiveData<List<HealthRecord>>
    
    @Query("SELECT * FROM health_records WHERE type = :type ORDER BY timestamp DESC")
    fun getHealthRecordsByType(type: HealthRecord.RecordType): LiveData<List<HealthRecord>>
    
    @Query("SELECT * FROM health_records WHERE timestamp BETWEEN :startTime AND :endTime ORDER BY timestamp DESC")
    fun getHealthRecordsByTimeRange(startTime: Long, endTime: Long): LiveData<List<HealthRecord>>
    
    @Query("SELECT * FROM health_records WHERE type = :type AND timestamp BETWEEN :startTime AND :endTime ORDER BY timestamp DESC")
    fun getHealthRecordsByTypeAndTimeRange(
        type: HealthRecord.RecordType,
        startTime: Long,
        endTime: Long
    ): LiveData<List<HealthRecord>>
    
    // Heart rate record methods
    @Insert
    suspend fun insertHeartRateRecord(record: HeartRateRecord): Long
    
    @Update
    suspend fun updateHeartRateRecord(record: HeartRateRecord)
    
    @Delete
    suspend fun deleteHeartRateRecord(record: HeartRateRecord)
    
    @Query("SELECT * FROM heart_rate_records WHERE healthRecordId = :healthRecordId")
    suspend fun getHeartRateRecordByHealthId(healthRecordId: Int): HeartRateRecord?
    
    @Transaction
    @Query("""
        SELECT hr.* FROM heart_rate_records hr
        INNER JOIN health_records h ON hr.healthRecordId = h.id
        WHERE h.timestamp BETWEEN :startTime AND :endTime
        ORDER BY h.timestamp DESC
    """)
    fun getHeartRateRecordsByTimeRange(startTime: Long, endTime: Long): LiveData<List<HeartRateRecord>>
    
    // SpO2 record methods
    @Insert
    suspend fun insertSpO2Record(record: SpO2Record): Long
    
    @Update
    suspend fun updateSpO2Record(record: SpO2Record)
    
    @Delete
    suspend fun deleteSpO2Record(record: SpO2Record)
    
    @Query("SELECT * FROM spo2_records WHERE healthRecordId = :healthRecordId")
    suspend fun getSpO2RecordByHealthId(healthRecordId: Int): SpO2Record?
    
    @Transaction
    @Query("""
        SELECT s.* FROM spo2_records s
        INNER JOIN health_records h ON s.healthRecordId = h.id
        WHERE h.timestamp BETWEEN :startTime AND :endTime
        ORDER BY h.timestamp DESC
    """)
    fun getSpO2RecordsByTimeRange(startTime: Long, endTime: Long): LiveData<List<SpO2Record>>
    
    // Stress record methods
    @Insert
    suspend fun insertStressRecord(record: StressRecord): Long
    
    @Update
    suspend fun updateStressRecord(record: StressRecord)
    
    @Delete
    suspend fun deleteStressRecord(record: StressRecord)
    
    @Query("SELECT * FROM stress_records WHERE healthRecordId = :healthRecordId")
    suspend fun getStressRecordByHealthId(healthRecordId: Int): StressRecord?
    
    @Transaction
    @Query("""
        SELECT s.* FROM stress_records s
        INNER JOIN health_records h ON s.healthRecordId = h.id
        WHERE h.timestamp BETWEEN :startTime AND :endTime
        ORDER BY h.timestamp DESC
    """)
    fun getStressRecordsByTimeRange(startTime: Long, endTime: Long): LiveData<List<StressRecord>>
    
    // Recommendation methods
    @Insert
    suspend fun insertRecommendation(recommendation: Recommendation): Long
    
    @Update
    suspend fun updateRecommendation(recommendation: Recommendation)
    
    @Delete
    suspend fun deleteRecommendation(recommendation: Recommendation)
    
    @Query("SELECT * FROM recommendations WHERE id = :id")
    suspend fun getRecommendationById(id: Int): Recommendation?
    
    @Query("SELECT * FROM recommendations ORDER BY timestamp DESC")
    fun getAllRecommendations(): LiveData<List<Recommendation>>
    
    @Query("SELECT * FROM recommendations WHERE relatedHealthRecordId = :healthRecordId")
    fun getRecommendationsForHealthRecord(healthRecordId: Int): LiveData<List<Recommendation>>
    
    @Query("SELECT * FROM recommendations WHERE category = :category ORDER BY timestamp DESC")
    fun getRecommendationsByCategory(category: Recommendation.RecommendationType): LiveData<List<Recommendation>>
    
    // Complex queries for combined data
    
    @Transaction
    suspend fun saveHeartRateMeasurement(bpm: Int, accuracy: Float, duration: Int, note: String? = null): Long {
        val healthRecord = HealthRecord(
            type = HealthRecord.RecordType.HEART_RATE,
            note = note
        )
        val healthRecordId = insertHealthRecord(healthRecord)
        
        val heartRateRecord = HeartRateRecord(
            healthRecordId = healthRecordId.toInt(),
            bpm = bpm,
            accuracy = accuracy,
            duration = duration
        )
        insertHeartRateRecord(heartRateRecord)
        
        return healthRecordId
    }
    
    @Transaction
    suspend fun saveSpO2Measurement(percentage: Int, accuracy: Float, duration: Int, note: String? = null): Long {
        val healthRecord = HealthRecord(
            type = HealthRecord.RecordType.SPO2,
            note = note
        )
        val healthRecordId = insertHealthRecord(healthRecord)
        
        val spO2Record = SpO2Record(
            healthRecordId = healthRecordId.toInt(),
            percentage = percentage,
            accuracy = accuracy,
            duration = duration
        )
        insertSpO2Record(spO2Record)
        
        return healthRecordId
    }
    
    @Transaction
    suspend fun saveStressMeasurement(level: StressRecord.StressLevel, hrvScore: Float? = null, note: String? = null): Long {
        val healthRecord = HealthRecord(
            type = HealthRecord.RecordType.STRESS,
            note = note
        )
        val healthRecordId = insertHealthRecord(healthRecord)
        
        val stressRecord = StressRecord(
            healthRecordId = healthRecordId.toInt(),
            level = level,
            hrvScore = hrvScore
        )
        insertStressRecord(stressRecord)
        
        return healthRecordId
    }
} 