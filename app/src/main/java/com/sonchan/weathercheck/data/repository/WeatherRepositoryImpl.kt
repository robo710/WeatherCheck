package com.sonchan.weathercheck.data.repository

import android.util.Log
import com.sonchan.weathercheck.data.remote.api.WeatherApi
import com.sonchan.weathercheck.domain.model.WeatherInfo
import com.sonchan.weathercheck.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: WeatherApi
) : WeatherRepository {

    override suspend fun getTodayWeatherInfo(
        baseDate: String,
        baseTime: String,
        nx: Int,
        ny: Int
    ): WeatherInfo {
        return try {
            val response = apiService.getWeatherForecast(
                baseDate = baseDate,
                baseTime = baseTime,
                nx = nx,
                ny = ny
            )
            val items = response.response.body.items.item

            val temps = items.filter { it.category == "TMP" }
                .associate { it.fcstTime to it.fcstValue.toInt() }

            val precipitation = items.filter { it.category == "POP" }
                .associate { it.fcstTime to it.fcstValue.toInt() }

            val maxTemp = items.firstOrNull { it.category == "TMX" }
                ?.fcstValue?.toFloat()?.toInt() ?: 0

            val minTemp = items.firstOrNull { it.category == "TMN" }
                ?.fcstValue?.toFloat()?.toInt() ?: 0

            val skyValue = items.firstOrNull { it.category == "SKY" }
                ?.fcstValue?.toInt() ?: 0

            val sky = when (skyValue) {
                1 -> "맑음"
                3 -> "구름 많음"
                4 -> "흐림"
                else -> "Unknown"
            }

            WeatherInfo(
                temps = temps,
                maxTemp = maxTemp,
                minTemp = minTemp,
                precipitation = precipitation,
                sky = sky
            )

        } catch (e: Exception) {
            Log.e("로그", "API 호출 실패: ${e.message}", e)
            WeatherInfo(
                temps = emptyMap(),
                maxTemp = 0,
                minTemp = 0,
                precipitation = emptyMap(),
                sky = ""
            )
        }
    }
}
