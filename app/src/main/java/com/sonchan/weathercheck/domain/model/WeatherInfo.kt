package com.sonchan.weathercheck.domain.model

data class WeatherInfo(
    val temps: Map<String, Map<String, Int>>,
    val maxTemp: Int,
    val minTemp: Int,
    val precipitation: Map<String, Map<String, Int>>,
    val sky: String,
)