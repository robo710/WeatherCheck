package com.sonchan.weathercheck.domain.repository

interface AlarmRepository {
    suspend fun saveAlarmTime(hour: Int, minute: Int)
}