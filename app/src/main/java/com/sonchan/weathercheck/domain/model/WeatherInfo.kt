package com.sonchan.weathercheck.domain.model

data class WeatherInfo(
    val maxTemp: Int,
    val minTemp: Int,
    val precipitation: Int,
    val sky: String,
)