package com.sonchan.weathercheck.receiver

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.sonchan.weathercheck.R
import com.sonchan.weathercheck.data.remote.api.WeatherApi
import com.sonchan.weathercheck.data.repository.WeatherRepositoryImpl
import com.sonchan.weathercheck.domain.model.WeatherInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val hour = intent?.getIntExtra("hour", 8) ?: 8
        val minute = intent?.getIntExtra("minute", 0) ?: 0

        val todayKey = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())

        val nx = 57
        val ny = 74

        getWeatherInfo(context, todayKey, "0200", nx, ny) { weatherInfo ->
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channelId = "weather_check_channel"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelId,
                    "날씨 알림",
                    NotificationManager.IMPORTANCE_HIGH
                )
                notificationManager.createNotificationChannel(channel)
            }

            val contentText = if (weatherInfo != null) {
                "최고 기온: ${weatherInfo.maxTemp[todayKey]}°C, 최저 기온: ${weatherInfo.minTemp[todayKey]}°C"
            } else {
                "오늘의 날씨를 확인하세요!"
            }

            val notification = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("WeatherCheck")
                .setContentText(contentText)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()

            notificationManager.notify(1, notification)

            scheduleNextAlarm(context, hour, minute)
        }
    }

    private fun getWeatherInfo(context: Context, baseDate: String, baseTime: String, nx: Int, ny: Int, callback: (WeatherInfo?) -> Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weatherApi = retrofit.create(WeatherApi::class.java)
        val weatherRepository = WeatherRepositoryImpl(weatherApi)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val weatherInfo = weatherRepository.getTodayWeatherInfo(baseDate, baseTime, nx, ny)
                callback(weatherInfo)
            } catch (e: Exception) {
                Log.e("AlarmReceiver", "날씨 정보 로드 실패: ${e.message}")
                callback(null)
            }
        }
    }


    private fun scheduleNextAlarm(context: Context, hour: Int, minute: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calendar = Calendar.getInstance().apply {
            add(Calendar.DATE, 1)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val newIntent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("hour", hour)
            putExtra("minute", minute)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            newIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
    }
}
