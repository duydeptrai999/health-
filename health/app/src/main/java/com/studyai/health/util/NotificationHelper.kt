package com.studyai.health.util

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.studyai.health.R
import com.studyai.health.notifications.NotificationReceiver
import java.util.*

/**
 * Helper class for managing notifications in the app
 */
object NotificationHelper {
    
    const val CHANNEL_REMINDERS = "health_reminders"
    const val CHANNEL_ALERTS = "health_alerts"
    
    private const val NOTIFICATION_ID_REMINDER = 1000
    private const val NOTIFICATION_ID_HEALTH_ALERT = 2000
    
    /**
     * Schedule a reminder notification
     * 
     * @param context The application context
     * @param reminderId The ID of the reminder
     * @param title The title of the notification
     * @param message The message of the notification
     * @param triggerTime The time to trigger the notification
     * @param isRepeating Whether the notification should repeat
     * @param intervalMillis The interval for repeating notifications
     */
    fun scheduleReminder(
        context: Context,
        reminderId: Int,
        title: String,
        message: String,
        triggerTime: Long,
        isRepeating: Boolean = false,
        intervalMillis: Long = AlarmManager.INTERVAL_DAY
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("notification_id", NOTIFICATION_ID_REMINDER + reminderId)
            putExtra("title", title)
            putExtra("message", message)
            putExtra("channel_id", CHANNEL_REMINDERS)
        }
        
        val pendingIntentFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminderId,
            intent,
            pendingIntentFlag
        )
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
            // Fall back to inexact alarms if we don't have permission
            if (isRepeating) {
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    triggerTime,
                    intervalMillis,
                    pendingIntent
                )
            } else {
                alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    triggerTime,
                    pendingIntent
                )
            }
        } else {
            // We can use exact alarms
            if (isRepeating) {
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    triggerTime,
                    intervalMillis,
                    pendingIntent
                )
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        triggerTime,
                        pendingIntent
                    )
                } else {
                    alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        triggerTime,
                        pendingIntent
                    )
                }
            }
        }
    }
    
    /**
     * Cancel a scheduled reminder
     * 
     * @param context The application context
     * @param reminderId The ID of the reminder to cancel
     */
    fun cancelReminder(context: Context, reminderId: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java)
        
        val pendingIntentFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminderId,
            intent,
            pendingIntentFlag
        )
        
        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
    }
    
    /**
     * Show a health alert notification
     * 
     * @param context The application context
     * @param title The title of the notification
     * @param message The message of the notification
     * @param contentIntent The intent to launch when the notification is tapped
     */
    fun showHealthAlert(
        context: Context,
        title: String,
        message: String,
        contentIntent: PendingIntent? = null
    ) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        val notification = NotificationCompat.Builder(context, CHANNEL_ALERTS)
            .setSmallIcon(R.drawable.ic_notification_alert)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .apply {
                contentIntent?.let { setContentIntent(it) }
            }
            .build()
        
        notificationManager.notify(NOTIFICATION_ID_HEALTH_ALERT, notification)
    }
} 