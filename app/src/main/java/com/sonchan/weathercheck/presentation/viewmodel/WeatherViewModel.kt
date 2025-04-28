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
            _weatherInfo.value = getTodayWeatherUseCase()
        }
    }
}