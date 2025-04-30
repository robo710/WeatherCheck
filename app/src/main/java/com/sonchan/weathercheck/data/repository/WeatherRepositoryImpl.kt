package com.sonchan.weathercheck.data.repository

import android.util.Log
import com.sonchan.weathercheck.R
import com.sonchan.weathercheck.data.remote.api.WeatherApi
import com.sonchan.weathercheck.domain.model.SkyInfo
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

            val temps = items
                .filter { it.category == "TMP" }
                .groupBy { it.fcstDate }
                .mapValues { entry ->
                    entry.value.associate { it.fcstTime to it.fcstValue.toInt() }
                }

            val precipitation = items
                .filter { it.category == "POP" }
                .groupBy { it.fcstDate }
                .mapValues { entry ->
                    entry.value.associate { it.fcstTime to it.fcstValue.toInt() }
                }

            val humidity = items
                .filter { it.category == "REH" }
                .groupBy { it.fcstDate }
                .mapValues { entry ->
                    entry.value.associate { it.fcstTime to it.fcstValue.toInt() }
                }

            val maxTemp = items.firstOrNull { it.category == "TMX" }
                ?.fcstValue?.toFloat()?.toInt() ?: 0

            val minTemp = items.firstOrNull { it.category == "TMN" }
                ?.fcstValue?.toFloat()?.toInt() ?: 0

            val sky = items
                .filter { it.category == "SKY" }
                .groupBy { it.fcstDate }
                .mapValues { entry ->
                    entry.value.associate {
                        it.fcstTime to when (it.fcstValue.toInt()) {
                            1 -> SkyInfo(
                                description = "맑음",
                                icon = R.drawable.sunny_icon,
                            )
                            3 -> SkyInfo(
                                description = "구름 많음",
                                icon = R.drawable.sun_cloud_icon,
                            )
                            4 -> SkyInfo(
                                description = "흐림",
                                icon = R.drawable.cloud_icon,
                            )
                            else ->  SkyInfo(
                                description = "알수없음",
                                icon = R.drawable.rainy_icon,
                            )
                        }
                    }
                }

            WeatherInfo(
                temps = temps,
                maxTemp = maxTemp,
                minTemp = minTemp,
                precipitation = precipitation,
                sky = sky,
                humidity = humidity
            )

        } catch (e: Exception) {
            Log.e("로그", "API 호출 실패: ${e.message}", e)
            WeatherInfo(
                temps = emptyMap(),
                maxTemp = 0,
                minTemp = 0,
                precipitation = emptyMap(),
                sky = emptyMap(),
                humidity = emptyMap(),
            )
        }
    }
}
