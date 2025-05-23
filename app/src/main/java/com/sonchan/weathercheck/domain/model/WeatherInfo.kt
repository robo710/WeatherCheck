package com.sonchan.weathercheck.domain.model

data class WeatherInfo(
    val temps: Map<String, Map<String, Int>>,
    val maxTemp: Map<String, Int>,
    val minTemp: Map<String, Int>,
    val precipitation: Map<String, Map<String, Int>>,
    val sky: Map<String, Map<String, SkyInfo>>,
    val humidity: Map<String, Map<String, Int>>,
)