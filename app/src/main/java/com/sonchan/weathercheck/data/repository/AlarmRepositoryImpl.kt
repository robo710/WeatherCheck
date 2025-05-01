package com.sonchan.weathercheck.data.repository

import com.sonchan.weathercheck.data.preference.AlarmPreference
import com.sonchan.weathercheck.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val alarmPreference: AlarmPreference
) : AlarmRepository {
    override suspend fun saveAlarmTime(hour: Int, minute: Int) {
        alarmPreference.saveAlarmTime(hour, minute)
    }

    override fun getAlarmHour(): Flow<Int> = alarmPreference.alarmHour
    override fun getAlarmMinute(): Flow<Int> = alarmPreference.alarmMinute
}
