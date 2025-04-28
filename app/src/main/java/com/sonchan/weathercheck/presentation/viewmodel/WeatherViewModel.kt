package com.sonchan.weathercheck.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonchan.weathercheck.domain.model.WeatherInfo
import com.sonchan.weathercheck.domain.usecase.GetTodayWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getTodayWeatherUseCase: GetTodayWeatherUseCase
): ViewModel(){
    private val _weatherInfo = MutableStateFlow<WeatherInfo?>(null)

    val weatherInfo: StateFlow<WeatherInfo?> = _weatherInfo

    fun getWeatherInfo(){
        viewModelScope.launch {
            val result = getTodayWeatherUseCase(
                baseDate = "20250428",
                baseTime = "0200",
                nx = 60,
                ny = 127,
            )
            _weatherInfo.value= result
        }
    }
}