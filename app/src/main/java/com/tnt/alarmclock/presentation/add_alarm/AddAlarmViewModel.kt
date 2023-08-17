package com.tnt.alarmclock.presentation.add_alarm

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tnt.alarmclock.data.local.entity.AlarmEntity
import com.tnt.alarmclock.data.local.repository.AlarmClockRepository
import com.tnt.alarmclock.domain.repository.AlarmScheduler
import com.tnt.alarmclock.domain.repository.AlarmSchedulerImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAlarmViewModel @Inject constructor(private val alarmClockRepository: AlarmClockRepository) :
    ViewModel() {
    var hour by mutableIntStateOf(0)
    var minute by mutableIntStateOf(0)
    var title by mutableStateOf("")
    var once by mutableStateOf(true)
    var showSheetForOnce by mutableStateOf(false)
    var showSheetForRepeat by mutableStateOf(false)
    val listRepeat = mutableStateListOf(false, false, false, false, false, false, false)

    fun updateAlarm(id: Long?, context: Context) {
        viewModelScope.launch {
            val alarm = AlarmEntity(
                title = title,
                time = "${hour.toString().padStart(2, '0')}:${
                    minute.toString().padStart(2, '0')
                }",
                isOn = true,
                sun = listRepeat[0],
                mon = listRepeat[1],
                tue = listRepeat[2],
                wed = listRepeat[3],
                thu = listRepeat[4],
                fri = listRepeat[5],
                sat = listRepeat[6],
                id = id ?: 0,
                once = once
            )
            alarmClockRepository.upsertAlarm(alarm)
            val alarmScheduler: AlarmScheduler = AlarmSchedulerImpl(context)
            alarmScheduler.schedule(alarm.toAlarm())
        }
    }
}