package com.studyai.health.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.studyai.health.data.models.HealthMetric
import com.studyai.health.data.models.HealthRecord
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * Data Access Object (DAO) for health records.
 * This interface provides methods to access and manipulate health record data in the database.
 */
@Dao
interface HealthRecordDao {
    /**
     * Get all health records ordered by timestamp (newest first).
     * @return Flow emitting lists of all health records
     */
    @Query("SELECT * FROM health_records ORDER BY timestamp DESC")
    fun getAllHealthRecords(): Flow<List<HealthRecord>>
    
    /**
     * Get health records for a specific metric.
     * @param metric The health metric to filter by
     * @return Flow emitting lists of health records for the specified metric
     */
    @Query("SELECT * FROM health_records WHERE metric = :metric ORDER BY timestamp DESC")
    fun getHealthRecordsByMetric(metric: HealthMetric): Flow<List<HealthRecord>>
    
    /**
     * Get health records within a date range.
     * @param startDate Start date of the range (inclusive)
     * @param endDate End date of the range (inclusive)
     * @return Flow emitting lists of health records within the date range
     */
    @Query("SELECT * FROM health_records WHERE timestamp BETWEEN :startDate AND :endDate ORDER BY timestamp DESC")
    fun getHealthRecordsByDateRange(startDate: Date, endDate: Date): Flow<List<HealthRecord>>
    
    /**
     * Get the latest health record for a specific metric.
     * @param metric The health metric
     * @return The most recent health record for the specified metric, or null if none exists
     */
    @Query("SELECT * FROM health_records WHERE metric = :metric ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestHealthRecord(metric: HealthMetric): HealthRecord?
    
    /**
     * Insert a new health record.
     * @param healthRecord The health record to insert
     * @return The ID of the inserted record
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHealthRecord(healthRecord: HealthRecord): Long
    
    /**
     * Insert multiple health records.
     * @param healthRecords The list of health records to insert
     * @return The list of IDs of the inserted records
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHealthRecords(healthRecords: List<HealthRecord>): List<Long>
    
    /**
     * Update an existing health record.
     * @param healthRecord The health record to update
     */
    @Update
    suspend fun updateHealthRecord(healthRecord: HealthRecord)
    
    /**
     * Delete a health record.
     * @param healthRecord The health record to delete
     */
    @Delete
    suspend fun deleteHealthRecord(healthRecord: HealthRecord)
    
    /**
     * Delete all health records of a specific metric.
     * @param metric The health metric
     */
    @Query("DELETE FROM health_records WHERE metric = :metric")
    suspend fun deleteAllByMetric(metric: HealthMetric)
    
    /**
     * Delete all health records.
     */
    @Query("DELETE FROM health_records")
    suspend fun deleteAllHealthRecords()
    
    /**
     * Get count of health records for a specific metric.
     * @param metric The health metric
     * @return The count of health records for the specified metric
     */
    @Query("SELECT COUNT(*) FROM health_records WHERE metric = :metric")
    suspend fun getCountByMetric(metric: HealthMetric): Int
} 