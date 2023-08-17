package com.tnt.alarmclock.domain.repository

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.tnt.alarmclock.core.services.AlarmReceiver
import com.tnt.alarmclock.data.local.models.Alarm
import java.time.LocalDateTime
import java.time.ZoneId

class AlarmSchedulerImpl(private val context: Context) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(alarm: Alarm) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("ALARM_TITLE", alarm.title)
            putExtra("ALARM_ID", alarm.id)
            Log.d("id in schedule", alarm.id.toString())
        }
        val alarmTime = LocalDateTime.now().plusSeconds(10).atZone(ZoneId.systemDefault())
            .toEpochSecond() * 1000L
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarmTime,
            PendingIntent.getBroadcast(
                context,
                alarm.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
        Log.e("Alarm", "Alarm set at $alarmTime")
    }

    override fun cancel(alarmItem: Alarm) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                alarmItem.hashCode(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}