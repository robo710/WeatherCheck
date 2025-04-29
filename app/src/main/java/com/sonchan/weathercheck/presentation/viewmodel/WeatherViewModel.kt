package com.sonchan.weathercheck.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonchan.weathercheck.domain.model.WeatherInfo
import com.sonchan.weathercheck.domain.usecase.GetTodayDateUseCase
import com.sonchan.weathercheck.domain.usecase.GetTodayWeatherUseCase
import com.sonchan.weathercheck.domain.usecase.NotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getTodayWeatherUseCase: GetTodayWeatherUseCase,
    private val notificationUseCase: NotificationUseCase,
    private val getTodayDateUseCase: GetTodayDateUseCase,
): ViewModel(){
    private val _weatherInfo = MutableStateFlow<WeatherInfo?>(null)
    private val _today = MutableStateFlow<String?>(null)

    val weatherInfo: StateFlow<WeatherInfo?> = _weatherInfo
    val today: StateFlow<String?> = _today

    init {
        _today.value = getTodayDateUseCase()
        getWeatherInfo(
            baseDate = _today.value!!,
            baseTime = "0200",
            nx = 60,
            ny = 127
        )
    }

    private fun getWeatherInfo(
        baseDate: String,
        baseTime: String,
        nx: Int,
        ny: Int,
        ){
        viewModelScope.launch {
            val result = getTodayWeatherUseCase(
                baseDate = baseDate,
                baseTime = baseTime,
                nx = nx,
                ny = ny
            )
            _weatherInfo.value= result
        }
    }

    fun getNotification(context: Context, icon: Int, title: String, text: String){
        getWeatherInfo(
            baseDate = _today.value!!,
            baseTime = "0200",
            nx = 60,
            ny = 127)
        notificationUseCase.showNotification(context = context, icon = icon, title = title, text = text)
    }
}