package com.sonchan.weathercheck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sonchan.weathercheck.presentation.screen.WeatherScreen
import com.sonchan.weathercheck.ui.theme.WeatherCheckTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherCheckTheme {
                WeatherScreen()
            }
        }
    }
}