package com.tnt.alarmclock.data.local.repository

import com.tnt.alarmclock.data.local.dao.AlarmDao
import com.tnt.alarmclock.data.local.entity.AlarmEntity
import kotlinx.coroutines.flow.Flow

class AlarmClockRepositoryImpl(private val alarmDao: AlarmDao) : AlarmClockRepository {
    override fun getAlarm(): Flow<List<AlarmEntity>> = alarmDao.getAlarm()

    override fun getAlarmByID(id: Long): Flow<AlarmEntity?> = alarmDao.getAlarmByID(id)

    override suspend fun upsertAlarm(item: AlarmEntity) = alarmDao.upsertAlarm(item)
}