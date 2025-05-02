package com.sonchan.weathercheck.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.sonchan.weathercheck.domain.usecase.GetTodayWeatherUseCase
import com.sonchan.weathercheck.domain.usecase.NotificationUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.text.SimpleDateFormat
import java.util.*

@HiltWorker
class WeatherWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val getTodayWeatherUseCase: GetTodayWeatherUseCase,
    private val notificationUseCase: NotificationUseCase
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        try {
            val now = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
            val timeFormat = SimpleDateFormat("HHmm", Locale.getDefault())

            val baseDate = dateFormat.format(now.time)
            val baseTime = "0200" // 예: 오전 5시 기준

            // 위도/경도 값 (예시로 고정된 위치 사용. 추후 사용자 설정에서 받아오기 가능)
            val nx = 60
            val ny = 127

            val weather = getTodayWeatherUseCase(baseDate, baseTime, nx, ny)

            val text = "최고 기온: ${weather.maxTemp}°C, 최저 기온: ${weather.minTemp}°C"
            notificationUseCase.showNotification(
                context = applicationContext,
                icon = com.sonchan.weathercheck.R.drawable.ic_launcher_foreground,
                title = "오늘의 날씨",
                text = text
            )

            return Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure()
        }
    }
}
