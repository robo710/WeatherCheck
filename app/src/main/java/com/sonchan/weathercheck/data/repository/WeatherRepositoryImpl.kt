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

            val maxTemp = items
                .filter { it.category == "TMX" }
                .groupBy { it.fcstDate }
                .mapValues { entry ->
                    entry.value.firstOrNull()?.fcstValue?.toFloat()?.toInt() ?: 0
                }

            val minTemp = items
                .filter { it.category == "TMN" }
                .groupBy { it.fcstDate }
                .mapValues { entry ->
                    entry.value.firstOrNull()?.fcstValue?.toFloat()?.toInt() ?: 0
                }

            val skyItems = items.filter { it.category == "SKY" }
            val ptyItems = items.filter { it.category == "PTY" }

            val skyGrouped = skyItems.groupBy { it.fcstDate }
            val ptyGrouped = ptyItems.groupBy { it.fcstDate }

            val sky = skyGrouped.mapValues { (date, skyList) ->
                skyList.associate { skyItem ->
                    val time = skyItem.fcstTime
                    val skyValue = skyItem.fcstValue.toInt()

                    val ptyValue = ptyGrouped[date]
                        ?.firstOrNull { it.fcstTime == time }
                        ?.fcstValue?.toInt() ?: 0

                    val skyInfo = when (ptyValue) {
                        1 -> SkyInfo("비", R.drawable.rainy_icon)
                        2 -> SkyInfo("비/눈", R.drawable.snowy_rainy_icon)
                        3 -> SkyInfo("눈", R.drawable.snowy_icon)
                        4 -> SkyInfo("소나기", R.drawable.rainy_icon)
                        else -> when (skyValue) {
                            1 -> SkyInfo("맑음", R.drawable.sunny_icon)
                            3 -> SkyInfo("구름 많음", R.drawable.sun_cloud_icon)
                            4 -> SkyInfo("흐림", R.drawable.cloud_icon)
                            else -> SkyInfo("알수없음", R.drawable.sunny_icon)
                        }
                    }

                    time to skyInfo
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
                maxTemp = emptyMap(),
                minTemp = emptyMap(),
                precipitation = emptyMap(),
                sky = emptyMap(),
                humidity = emptyMap(),
            )
        }
    }
}
