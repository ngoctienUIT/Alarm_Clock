package com.tnt.alarmclock.core.services

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.room.Room
import com.tnt.alarmclock.R
import com.tnt.alarmclock.data.local.database.AlarmClockDatabase
import com.tnt.alarmclock.data.local.repository.AlarmClockRepositoryImpl
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.random.Random

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("ALARM_TITLE") ?: ""
        val id = intent?.getLongExtra("ALARM_ID", 0)
        Log.d("id in Receive", id.toString())

        val channelId = "com.tnt.alarmclock"
        context?.let { ctx ->
            val notificationManager =
                ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val builder = NotificationCompat.Builder(ctx, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Alarm Clock")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
            notificationManager.notify(Random(999).nextInt(), builder.build())
            id?.let { turnOffAlarm(it, ctx) }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun turnOffAlarm(id: Long, context: Context) {
        val database =
            Room.databaseBuilder(context, AlarmClockDatabase::class.java, "AlarmClockDatabase")
                .build()
        val alarmDao = database.alarmDao()
        val alarmClockRepository = AlarmClockRepositoryImpl(alarmDao)
        GlobalScope.launch {
            val alarm = alarmClockRepository.getAlarmByID(id).first()
            Log.d("alarm", alarm.toString())
            if (alarm != null && alarm.once) {
                alarmClockRepository.upsertAlarm(alarm.changeState(false))
            }
        }
    }
}