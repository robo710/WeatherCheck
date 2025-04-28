package com.sonchan.weathercheck.domain.usecase

import com.sonchan.weathercheck.domain.model.WeatherInfo
import com.sonchan.weathercheck.domain.repository.WeatherRepository
import javax.inject.Inject

class GetTodayWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
){
    suspend operator fun invoke(
        baseDate: String,
        baseTime: String,
        nx: Int,
        ny: Int
    ): WeatherInfo {
        return repository.getTodayWeatherInfo(
            baseDate = baseDate,
            baseTime = baseTime,
            nx = nx,
            ny = ny
        )
    }
}