package com.tnt.alarmclock.presentation.alarm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tnt.alarmclock.data.local.entity.AlarmEntity
import com.tnt.alarmclock.data.local.repository.AlarmClockRepository
import com.tnt.alarmclock.domain.repository.AlarmScheduler
import com.tnt.alarmclock.domain.repository.AlarmSchedulerImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(private val alarmClockRepository: AlarmClockRepository) :
    ViewModel() {

    val listAlarm: StateFlow<List<AlarmEntity>> =
        alarmClockRepository.getAlarm().map { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = listOf()
            )

    fun changeSwitch(id: Long, switch: Boolean, context: Context) {
        viewModelScope.launch {
            val alarm = alarmClockRepository.getAlarmByID(id).first()
            val alarmScheduler: AlarmScheduler = AlarmSchedulerImpl(context)
            if (alarm != null) {
                if (switch) {
                    alarmScheduler.schedule(alarm.toAlarm())
                } else {
                    alarmScheduler.cancel(alarm.toAlarm())
                }
                alarmClockRepository.upsertAlarm(
                    AlarmEntity(
                        alarm.title,
                        alarm.time,
                        switch,
                        alarm.mon,
                        alarm.tue,
                        alarm.wed,
                        alarm.thu,
                        alarm.fri,
                        alarm.sat,
                        alarm.sun,
                        alarm.id
                    )
                )
            }
        }
    }

    init {
//        viewModelScope.launch {
//            alarmClockRepository.upsertAlarm(
//                AlarmEntity(
//                    "Work",
//                    "9:30",
//                    isOn = false,
//                    mon = true,
//                    tue = true,
//                    wed = true,
//                    thu = false,
//                    fri = true,
//                    sat = true,
//                    sun = true,
//                )
//            )
//        }
    }
}