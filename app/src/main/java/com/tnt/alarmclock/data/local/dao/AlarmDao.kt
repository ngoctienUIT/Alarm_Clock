package com.tnt.alarmclock.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.tnt.alarmclock.data.local.entity.AlarmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {
    @Query("SELECT * from Alarm")
    fun getAlarm(): Flow<List<AlarmEntity>>

    @Query("SELECT * from Alarm WHERE id = :id")
    fun getAlarmByID(id: Long): Flow<AlarmEntity?>

    @Upsert()
    suspend fun upsertAlarm(item: AlarmEntity)
}