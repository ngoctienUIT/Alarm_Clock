package com.tnt.alarmclock.data.local.repository

import com.tnt.alarmclock.data.local.entity.AlarmEntity
import kotlinx.coroutines.flow.Flow

interface AlarmClockRepository {
    fun getAlarm(): Flow<List<AlarmEntity>>

    fun getAlarmByID(id: Long): Flow<AlarmEntity?>

    suspend fun upsertAlarm(item: AlarmEntity)
}