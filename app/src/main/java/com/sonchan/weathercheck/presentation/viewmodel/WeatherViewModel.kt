package com.sonchan.weathercheck.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sonchan.weathercheck.data.preference.AlarmPreference
import com.sonchan.weathercheck.domain.model.TodayWeatherItem
import com.sonchan.weathercheck.domain.model.WeatherInfo
import com.sonchan.weathercheck.domain.repository.AlarmRepository
import com.sonchan.weathercheck.domain.usecase.GetTodayDateUseCase
import com.sonchan.weathercheck.domain.usecase.GetTodayWeatherUseCase
import com.sonchan.weathercheck.domain.usecase.NotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
    private val alarmRepository: AlarmRepository,
): ViewModel(){
    private val _weatherInfo = MutableStateFlow<WeatherInfo?>(null)
    private val _today = MutableStateFlow<String>("")
    private val _alartHour = MutableStateFlow<Int>(8)
    private val _alartMinute = MutableStateFlow<Int>(0)

    val weatherInfo: StateFlow<WeatherInfo?> = _weatherInfo
    val today: StateFlow<String> = _today
    val alartHour: StateFlow<Int> = _alartHour
    val alartMinute: StateFlow<Int> = _alartMinute

    init {
        _today.value = getTodayDateUseCase()
        getWeatherInfo(
            baseDate = _today.value!!,
            baseTime = "0200",
            nx = 57,
            ny = 74
        )
        viewModelScope.launch {
            alarmRepository.getAlarmHour().collect {
                _alartHour.value = it
            }
        }
        viewModelScope.launch {
            alarmRepository.getAlarmMinute().collect {
                _alartMinute.value = it
            }
        }
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

    fun saveAlarmTime(hour: Int, minute: Int) {
        viewModelScope.launch {
            alarmRepository.saveAlarmTime(hour, minute)
            _alartHour.value = hour
            _alartMinute.value = minute
        }
        Log.d("로그", "Updated _alartHour - ${_alartHour.value}")
        Log.d("로그", "Updated _alartMinute - ${_alartMinute.value}")
    }
}