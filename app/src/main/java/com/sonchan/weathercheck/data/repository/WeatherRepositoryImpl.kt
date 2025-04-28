package com.sonchan.weathercheck.data.repository

import android.util.Log
import com.sonchan.weathercheck.BuildConfig
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
        try {
            val response = apiService.getWeatherForecast(
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
        } catch (e: Exception){
            Log.e("로그", "API 호출 실패: ${e.message}", e)
            return WeatherInfo(
                maxTemp = 0,
                minTemp = 0,
                precipitation = 0
            )
        }
    }
}