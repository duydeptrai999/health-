package com.studyai.health.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.studyai.health.HealthApplication
import com.studyai.health.data.repository.ReminderRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * BroadcastReceiver that reschedules all reminders when the device reboots
 */
class BootReceiver : BroadcastReceiver() {
    
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val reminderRepository = HealthApplication.instance.reminderRepository
            
            // Reschedule all active reminders
            CoroutineScope(Dispatchers.IO).launch {
                reminderRepository.getActiveRemindersSync().forEach { reminder ->
                    // Calculate next trigger time based on reminder schedule
                    val nextTriggerTime = reminder.calculateNextTriggerTime()
                    
                    if (nextTriggerTime > System.currentTimeMillis()) {
                        // Only schedule if the trigger time is in the future
                        com.studyai.health.util.NotificationHelper.scheduleReminder(
                            context,
                            reminder.id,
                            reminder.title,
                            reminder.message,
                            nextTriggerTime,
                            reminder.isRepeating,
                            reminder.intervalMillis
                        )
                    }
                }
            }
        }
    }
} 