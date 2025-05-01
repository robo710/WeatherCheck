package com.sonchan.weathercheck.domain.repository

import kotlinx.coroutines.flow.Flow

interface AlarmRepository {
    suspend fun saveAlarmTime(hour: Int, minute: Int)
    fun getAlarmHour(): Flow<Int>
    fun getAlarmMinute(): Flow<Int>
}