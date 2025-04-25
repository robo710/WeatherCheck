package com.sonchan.weathercheck.data.repository

import com.sonchan.weathercheck.data.remote.api.WeatherApi
import com.sonchan.weathercheck.data.remote.model.WeatherResponse
import com.sonchan.weathercheck.domain.model.WeatherInfo
import com.sonchan.weathercheck.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: WeatherApi
): WeatherRepository{
    override suspend fun getTodayWeatherInfo(): WeatherInfo {
        val response = apiService.getWeatherForecast(
            numOfRows = TODO(),
            pageNo = TODO(),
            baseDate = TODO(),
            baseTime = TODO(),
            nx = TODO(),
            ny = TODO()
        )
    }
}