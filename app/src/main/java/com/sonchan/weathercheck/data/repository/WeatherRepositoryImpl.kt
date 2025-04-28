package com.sonchan.weathercheck.data.repository

import com.sonchan.weathercheck.data.remote.api.WeatherApi
import com.sonchan.weathercheck.data.remote.model.WeatherResponse
import com.sonchan.weathercheck.domain.model.WeatherInfo
import com.sonchan.weathercheck.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: WeatherApi
): WeatherRepository{
    override suspend fun getTodayWeatherInfo(
        baseDate: String,
        baseTime: String,
        nx: Int,
        ny: Int
    ): WeatherInfo {
        val response = apiService.getWeatherForecast(
            numOfRows = 1000,
            pageNo = 1,
            baseDate = baseDate,
            baseTime = baseTime,
            nx = nx,
            ny = ny
        )
        val items = response.response.body.items.item

        val temps = items.filter { it.category == "TMP" }
            .map { it.fcstValue.toInt() }

        val precipitation = items.firstOrNull { it.category == "POP" }?.fcstValue?.toInt() ?: 0

        return WeatherInfo(
            maxTemp = temps.maxOrNull() ?: 0,
            minTemp = temps.minOrNull() ?: 0,
            precipitation = precipitation
        )
    }
}