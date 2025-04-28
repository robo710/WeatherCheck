package com.sonchan.weathercheck.presentation.viewmodel

import android.util.Log
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

    init {
        getWeatherInfo(
            baseDate = "20250428", // 오늘 날짜 적어주기
            baseTime = "0200",      // 02시 기준
            nx = 60,                // 대충 서울 어딘가
            ny = 127
        )
    }

    fun getWeatherInfo(
        baseDate: String,
        baseTime: String,
        nx: Int,
        ny: Int,
        ){
        viewModelScope.launch {
            val result = getTodayWeatherUseCase(
                baseDate = baseDate, // 오늘 날짜 적어주기
                baseTime = baseTime,      // 02시 기준
                nx = nx,                // 대충 서울 어딘가
                ny = ny
            )
            _weatherInfo.value= result
        }
    }
}