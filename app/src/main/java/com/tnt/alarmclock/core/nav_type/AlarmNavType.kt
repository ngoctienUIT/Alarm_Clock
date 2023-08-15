package com.tnt.alarmclock.core.nav_type

import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson
import com.tnt.alarmclock.data.local.models.Alarm

class AlarmNavType : NavType<Alarm>(isNullableAllowed = true) {
    override fun get(bundle: Bundle, key: String): Alarm? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, Alarm::class.java)
        } else {
            @Suppress("DEPRECATION") bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): Alarm {
        return Gson().fromJson(value, Alarm::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Alarm) {
        bundle.putParcelable(key, value)
    }
}