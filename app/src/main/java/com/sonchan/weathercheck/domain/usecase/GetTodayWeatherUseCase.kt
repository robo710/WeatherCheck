package com.sonchan.weathercheck.domain.usecase

import com.sonchan.weathercheck.domain.model.WeatherInfo
import com.sonchan.weathercheck.domain.repository.WeatherRepository
import javax.inject.Inject

class GetTodayWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
){
    suspend operator fun invoke(): WeatherInfo {
        return repository.getTodayWeatherInfo()
    }
}