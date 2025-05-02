package com.sonchan.weathercheck.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import com.sonchan.weathercheck.R
import com.sonchan.weathercheck.domain.usecase.GetTodayWeatherUseCase
import com.sonchan.weathercheck.domain.usecase.NotificationUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.text.SimpleDateFormat

@HiltWorker
class WeatherWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val getTodayWeatherUseCase: GetTodayWeatherUseCase,
    private val notificationUseCase: NotificationUseCase
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val today = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
        val baseTime = "0200"
        val nx = 57
        val ny = 74

        return try {
            val weather = getTodayWeatherUseCase(today, baseTime, nx, ny)
            val maxTemp = weather.maxTemp[today] ?: 0
            val minTemp = weather.minTemp[today] ?: 0
            val text = "최고 기온: ${maxTemp}°C, 최저 기온: ${minTemp}°C"

            notificationUseCase.showNotification(
                context = applicationContext,
                icon = R.drawable.ic_launcher_foreground,
                title = "WeatherCheck",
                text = text
            )

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
