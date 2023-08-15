package com.tnt.alarmclock.data.local.models

import android.net.Uri
import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class Alarm(
    val title: String,
    val time: String,
    val isOn: Boolean,
    val mon: Boolean,
    val tue: Boolean,
    val wed: Boolean,
    val thu: Boolean,
    val fri: Boolean,
    val sat: Boolean,
    val sun: Boolean,
    val id: Long = 0,
): Parcelable {
    override fun toString(): String {
        return Uri.encode(Gson().toJson(this))
    }
}