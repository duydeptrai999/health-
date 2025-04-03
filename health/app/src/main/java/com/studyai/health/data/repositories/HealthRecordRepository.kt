package com.studyai.health.data.repositories

import com.studyai.health.data.dao.HealthRecordDao
import com.studyai.health.data.models.HealthMetric
import com.studyai.health.data.models.HealthRecord
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository that handles operations related to health records.
 * This class provides a clean API for data access to the rest of the application.
 */
@Singleton
class HealthRecordRepository @Inject constructor(
    private val healthRecordDao: HealthRecordDao
) {
    /**
     * Get all health records as a Flow.
     * @return Flow emitting lists of all health records ordered by timestamp (newest first)
     */
    fun getAllHealthRecords(): Flow<List<HealthRecord>> {
        return healthRecordDao.getAllHealthRecords()
    }
    
    /**
     * Get health records for a specific metric as a Flow.
     * @param metric The health metric to filter by
     * @return Flow emitting lists of health records for the specified metric
     */
    fun getHealthRecordsByMetric(metric: HealthMetric): Flow<List<HealthRecord>> {
        return healthRecordDao.getHealthRecordsByMetric(metric)
    }
    
    /**
     * Get health records within a date range as a Flow.
     * @param startDate Start date of the range (inclusive)
     * @param endDate End date of the range (inclusive)
     * @return Flow emitting lists of health records within the date range
     */
    fun getHealthRecordsByDateRange(startDate: Date, endDate: Date): Flow<List<HealthRecord>> {
        return healthRecordDao.getHealthRecordsByDateRange(startDate, endDate)
    }
    
    /**
     * Get the latest health record for a specific metric.
     * @param metric The health metric
     * @return The most recent health record for the specified metric, or null if none exists
     */
    suspend fun getLatestHealthRecord(metric: HealthMetric): HealthRecord? {
        return healthRecordDao.getLatestHealthRecord(metric)
    }
    
    /**
     * Insert a new health record.
     * @param healthRecord The health record to insert
     * @return The ID of the inserted record
     */
    suspend fun insertHealthRecord(healthRecord: HealthRecord): Long {
        return healthRecordDao.insertHealthRecord(healthRecord)
    }
    
    /**
     * Update an existing health record.
     * @param healthRecord The health record to update
     */
    suspend fun updateHealthRecord(healthRecord: HealthRecord) {
        healthRecordDao.updateHealthRecord(healthRecord)
    }
    
    /**
     * Delete a health record.
     * @param healthRecord The health record to delete
     */
    suspend fun deleteHealthRecord(healthRecord: HealthRecord) {
        healthRecordDao.deleteHealthRecord(healthRecord)
    }
    
    /**
     * Delete all health records of a specific metric.
     * @param metric The health metric
     */
    suspend fun deleteAllByMetric(metric: HealthMetric) {
        healthRecordDao.deleteAllByMetric(metric)
    }
} 