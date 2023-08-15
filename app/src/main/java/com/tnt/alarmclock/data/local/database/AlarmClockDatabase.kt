package com.tnt.alarmclock.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tnt.alarmclock.data.local.dao.AlarmDao
import com.tnt.alarmclock.data.local.entity.AlarmEntity

@Database(
    entities = [AlarmEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AlarmClockDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}