package com.studyai.health.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.studyai.health.data.model.Reminder

/**
 * Data Access Object for reminders
 */
@Dao
interface ReminderDao {
    
    @Insert
    suspend fun insertReminder(reminder: Reminder): Long
    
    @Update
    suspend fun updateReminder(reminder: Reminder)
    
    @Delete
    suspend fun deleteReminder(reminder: Reminder)
    
    @Query("SELECT * FROM reminders WHERE id = :id")
    suspend fun getReminderById(id: Int): Reminder?
    
    @Query("SELECT * FROM reminders ORDER BY timeHour, timeMinute ASC")
    fun getAllReminders(): LiveData<List<Reminder>>
    
    @Query("SELECT * FROM reminders WHERE isEnabled = 1 ORDER BY timeHour, timeMinute ASC")
    fun getActiveReminders(): LiveData<List<Reminder>>
    
    @Query("SELECT * FROM reminders WHERE isEnabled = 1")
    suspend fun getActiveReminders(): List<Reminder>
    
    @Query("SELECT * FROM reminders WHERE type = :type ORDER BY timeHour, timeMinute ASC")
    fun getRemindersByType(type: Reminder.ReminderType): LiveData<List<Reminder>>
    
    @Query("UPDATE reminders SET isEnabled = :isEnabled WHERE id = :id")
    suspend fun updateReminderStatus(id: Int, isEnabled: Boolean)
    
    @Query("DELETE FROM reminders WHERE id = :id")
    suspend fun deleteReminderById(id: Int)
} 