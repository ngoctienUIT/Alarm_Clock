package com.tnt.alarmclock.domain.repository

import com.tnt.alarmclock.data.local.models.Alarm

interface AlarmScheduler {
    fun schedule(alarmItem: Alarm)
    fun cancel(alarmItem: Alarm)
}