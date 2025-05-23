package com.sonchan.weathercheck

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.sonchan.weathercheck.presentation.screen.WeatherScreenRoute
import com.sonchan.weathercheck.ui.theme.WeatherCheckTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 알림 권한 요청 추가
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // 안드로이드 13 이상
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }

        setContent {
            WeatherCheckTheme {
                WeatherScreenRoute()
            }
        }
    }
}