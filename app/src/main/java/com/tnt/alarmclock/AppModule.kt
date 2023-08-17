package com.tnt.alarmclock

import android.content.Context
import androidx.room.Room
import com.tnt.alarmclock.data.local.dao.AlarmDao
import com.tnt.alarmclock.data.local.database.AlarmClockDatabase
import com.tnt.alarmclock.data.local.repository.AlarmClockRepository
import com.tnt.alarmclock.data.local.repository.AlarmClockRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideBeenAloneDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AlarmClockDatabase::class.java, "AlarmClockDatabase")
            .build()

    @Singleton
    @Provides
    fun provideAlarmDao(alarmClockDatabase: AlarmClockDatabase) = alarmClockDatabase.alarmDao()

    @Singleton
    @Provides
    fun provideInventoryRepository(alarmDao: AlarmDao): AlarmClockRepository =
        AlarmClockRepositoryImpl(alarmDao)
}