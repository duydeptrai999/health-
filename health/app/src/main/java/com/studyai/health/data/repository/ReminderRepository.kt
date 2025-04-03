package com.studyai.health.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.studyai.health.data.database.ReminderDao
import com.studyai.health.data.model.Reminder
import com.studyai.health.util.NotificationHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for reminders
 */
class ReminderRepository(private val reminderDao: ReminderDao) {
    
    // Basic CRUD operations
    fun getAllReminders() = reminderDao.getAllReminders()
    
    fun getActiveReminders() = reminderDao.getActiveReminders()
    
    suspend fun getActiveRemindersSync(): List<Reminder> =
        withContext(Dispatchers.IO) {
            reminderDao.getActiveRemindersSync()
        }
    
    fun getRemindersByType(type: Reminder.ReminderType) =
        reminderDao.getRemindersByType(type)
    
    suspend fun getReminderById(id: Int): Reminder? =
        withContext(Dispatchers.IO) {
            reminderDao.getReminderById(id)
        }
    
    suspend fun insertReminder(reminder: Reminder): Long =
        withContext(Dispatchers.IO) {
            reminderDao.insertReminder(reminder)
        }
    
    suspend fun updateReminder(reminder: Reminder) =
        withContext(Dispatchers.IO) {
            reminderDao.updateReminder(reminder)
        }
    
    suspend fun deleteReminder(reminder: Reminder) =
        withContext(Dispatchers.IO) {
            reminderDao.deleteReminder(reminder)
        }
    
    suspend fun deleteReminderById(id: Int) =
        withContext(Dispatchers.IO) {
            reminderDao.deleteReminderById(id)
        }
    
    suspend fun updateReminderStatus(id: Int, isEnabled: Boolean) =
        withContext(Dispatchers.IO) {
            reminderDao.updateReminderStatus(id, isEnabled)
        }
    
    /**
     * Schedule a reminder in the system
     */
    fun scheduleReminder(context: Context, reminder: Reminder) {
        val nextTriggerTime = reminder.calculateNextTriggerTime()
        
        if (reminder.isEnabled && nextTriggerTime > System.currentTimeMillis()) {
            val title = when (reminder.type) {
                Reminder.ReminderType.MEASUREMENT -> context.getString(com.studyai.health.R.string.schedule_type_measurement)
                Reminder.ReminderType.WATER -> context.getString(com.studyai.health.R.string.schedule_type_water)
                Reminder.ReminderType.EXERCISE -> context.getString(com.studyai.health.R.string.schedule_type_exercise)
                Reminder.ReminderType.MEDICATION -> context.getString(com.studyai.health.R.string.schedule_type_medication)
                Reminder.ReminderType.CUSTOM -> context.getString(com.studyai.health.R.string.schedule_type_custom)
            }
            
            NotificationHelper.scheduleReminder(
                context,
                reminder.id,
                reminder.title,
                reminder.message,
                nextTriggerTime,
                reminder.isRepeating,
                if (reminder.isRepeating) reminder.intervalMillis else 0
            )
        }
    }
    
    /**
     * Cancel a scheduled reminder
     */
    fun cancelReminder(context: Context, reminderId: Int) {
        NotificationHelper.cancelReminder(context, reminderId)
    }
    
    /**
     * Schedule all active reminders
     */
    suspend fun scheduleAllActiveReminders(context: Context) {
        val activeReminders = getActiveRemindersSync()
        
        activeReminders.forEach { reminder ->
            scheduleReminder(context, reminder)
        }
    }
    
    /**
     * Save a new reminder and schedule it
     */
    suspend fun saveAndScheduleReminder(
        context: Context,
        title: String,
        message: String,
        timeHour: Int,
        timeMinute: Int,
        type: Reminder.ReminderType,
        isRepeating: Boolean = false,
        repeatDays: Int = 0,
        intervalMillis: Long = 0
    ): Long {
        val reminder = Reminder(
            title = title,
            message = message,
            timeHour = timeHour,
            timeMinute = timeMinute,
            type = type,
            isRepeating = isRepeating,
            repeatDays = repeatDays,
            intervalMillis = intervalMillis
        )
        
        val id = insertReminder(reminder)
        val savedReminder = reminder.copy(id = id.toInt())
        
        scheduleReminder(context, savedReminder)
        
        return id
    }
} 