package com.sonchan.weathercheck.domain.model

data class TodayWeatherItem(
    val time: String,
    val temp: Int,
    val pop: Int,
    val sky: SkyInfo
)