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
): WeatherRepository {
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
                .map { it.fcstValue.toFloat().toInt() }
            val maxTemp = items.firstOrNull { it.category == "TMX" }?.fcstValue?.toFloat()?.toInt() ?: 0
            val minTemp = items.firstOrNull { it.category == "TMN" }?.fcstValue?.toFloat()?.toInt() ?: 0
            Log.d("로그", "maxTemp: $maxTemp")
            Log.d("로그", "minTemp: $minTemp")

            // "POP" 카테고리에서 강수 확률 추출
            val precipitation = items.firstOrNull { it.category == "POP" }?.fcstValue?.toInt() ?: 0

            return WeatherInfo(
                maxTemp = maxTemp,
                minTemp = minTemp,
                precipitation = precipitation
            )
        } catch (e: Exception) {
            Log.e("로그", "API 호출 실패: ${e.message}", e)
            return WeatherInfo(
                maxTemp = 0,
                minTemp = 0,
                precipitation = 0
            )
        }
    }
}
