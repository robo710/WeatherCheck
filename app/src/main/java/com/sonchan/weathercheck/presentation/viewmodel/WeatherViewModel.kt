package com.sonchan.weathercheck.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonchan.weathercheck.domain.model.TodayWeatherItem
import com.sonchan.weathercheck.domain.model.WeatherInfo
import com.sonchan.weathercheck.domain.usecase.GetTodayDateUseCase
import com.sonchan.weathercheck.domain.usecase.GetTodayWeatherUseCase
import com.sonchan.weathercheck.domain.usecase.NotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getTodayWeatherUseCase: GetTodayWeatherUseCase,
    private val notificationUseCase: NotificationUseCase,
    private val getTodayDateUseCase: GetTodayDateUseCase,
): ViewModel(){
    private val _weatherInfo = MutableStateFlow<WeatherInfo?>(null)
    private val _today = MutableStateFlow<String>("")

    val weatherInfo: StateFlow<WeatherInfo?> = _weatherInfo
    val today: StateFlow<String> = _today

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

    fun getNearestWeatherItem(): TodayWeatherItem? {
        val todayKey = _today.value
        val weather = _weatherInfo.value

        val items = weather?.temps?.get(todayKey)?.mapNotNull { (time, temp) ->
            val pop = weather.precipitation[todayKey]?.get(time)
            val sky = weather.sky[todayKey]?.get(time)
            val humidity = weather.humidity[todayKey]?.get(time)

            if (pop != null && sky != null && humidity != null) {
                TodayWeatherItem(time, temp, pop, sky, humidity)
            } else null
        } ?: return null

        val currentTime = SimpleDateFormat("HHmm", Locale.getDefault()).format(Date())
        return items.minByOrNull {
            kotlin.math.abs(it.time.toInt() - currentTime.toInt())
        }
    }
}