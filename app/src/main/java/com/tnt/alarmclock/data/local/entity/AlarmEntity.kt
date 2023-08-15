package com.tnt.alarmclock.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tnt.alarmclock.data.local.models.Alarm

@Entity(tableName = "Alarm")
data class AlarmEntity(
    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "time")
    val time: String,

    @ColumnInfo(name = "isOn")
    val isOn: Boolean,

    @ColumnInfo(name = "mon")
    val mon: Boolean,

    @ColumnInfo(name = "tue")
    val tue: Boolean,

    @ColumnInfo(name = "wed")
    val wed: Boolean,

    @ColumnInfo(name = "thu")
    val thu: Boolean,

    @ColumnInfo(name = "fri")
    val fri: Boolean,

    @ColumnInfo(name = "sat")
    val sat: Boolean,

    @ColumnInfo(name = "sun")
    val sun: Boolean,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
) {
    fun getListWeekday(): List<Boolean> = listOf(mon, tue, wed, thu, fri, sat, sun)

    fun toAlarm(): Alarm = Alarm(title, time, isOn, mon, tue, wed, thu, fri, sat, sun, id)
}