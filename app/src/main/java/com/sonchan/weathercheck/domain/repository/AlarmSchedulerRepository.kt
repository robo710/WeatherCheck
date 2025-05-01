package com.sonchan.weathercheck.domain.repository

interface AlarmSchedulerRepository {
    fun schedule(hour: Int, minute: Int)
}