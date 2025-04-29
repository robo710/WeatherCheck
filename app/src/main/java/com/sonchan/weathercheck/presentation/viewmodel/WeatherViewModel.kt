package com.sonchan.weathercheck.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonchan.weathercheck.domain.model.WeatherInfo
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
): ViewModel(){
    private val _weatherInfo = MutableStateFlow<WeatherInfo?>(null)

    val weatherInfo: StateFlow<WeatherInfo?> = _weatherInfo

    init {
        getWeatherInfo(
            baseDate = "20250428",
            baseTime = "0200",
            nx = 60,
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
                baseDate = baseDate,
                baseTime = baseTime,
                nx = nx,
                ny = ny
            )
            _weatherInfo.value= result
        }
    }

    fun getNotification(context: Context, icon: Int, title: String, text: String){
        notificationUseCase.showNotification(context = context, icon = icon, title = title, text = text)
    }
}