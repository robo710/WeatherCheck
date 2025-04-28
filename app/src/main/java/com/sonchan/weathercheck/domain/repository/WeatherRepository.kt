package com.sonchan.weathercheck.domain.repository

import com.sonchan.weathercheck.domain.model.WeatherInfo

interface WeatherRepository {
    suspend fun getTodayWeatherInfo(): WeatherInfo
}