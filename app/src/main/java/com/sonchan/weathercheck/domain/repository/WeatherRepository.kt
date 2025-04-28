package com.sonchan.weathercheck.domain.repository

import com.sonchan.weathercheck.domain.model.WeatherInfo

interface WeatherRepository {
    suspend fun getTodayWeatherInfo(
        baseDate: String,
        baseTime: String,
        nx: Int,
        ny: Int
    ): WeatherInfo
}