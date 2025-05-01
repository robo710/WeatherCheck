package com.sonchan.weathercheck.presentation.viewmodel

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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
import com.sonchan.weathercheck.receiver.AlarmReceiver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
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
    private val _alarmHour = MutableStateFlow<Int>(8)
    private val _alarmMinute = MutableStateFlow<Int>(0)

    val weatherInfo: StateFlow<WeatherInfo?> = _weatherInfo
    val today: StateFlow<String> = _today
    val alarmHour: StateFlow<Int> = _alarmHour
    val alarmMinute: StateFlow<Int> = _alarmMinute

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
                _alarmHour.value = it
            }
        }
        viewModelScope.launch {
            alarmRepository.getAlarmMinute().collect {
                _alarmMinute.value = it
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

    fun saveAlarmTime(hour: Int, minute: Int, context: Context) {
        viewModelScope.launch {
            alarmRepository.saveAlarmTime(hour, minute)
            _alarmHour.value = hour
            _alarmMinute.value = minute
            scheduleDailyNotification(context, hour, minute)
        }
        Log.d("로그", "Updated _alartHour - ${_alarmHour.value}")
        Log.d("로그", "Updated _alartMinute - ${_alarmMinute.value}")
    }

    private fun scheduleDailyNotification(context: Context, hour: Int, minute: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // 시간 설정
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            // 이미 지난 시간이라면 다음 날로 설정
            if (before(Calendar.getInstance())) {
                add(Calendar.DATE, 1)
            }
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

}