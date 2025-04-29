package com.sonchan.weathercheck.presentation.component.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sonchan.weathercheck.presentation.component.preview.DarkThemeDevicePreviews
import com.sonchan.weathercheck.presentation.component.preview.DevicePreviews
import com.sonchan.weathercheck.ui.theme.WeatherCheckTheme

@Composable
fun TodayWeatherList(
    modifier: Modifier = Modifier
){
    Column(
        modifier
    ) {
    }
}

@DevicePreviews
@DarkThemeDevicePreviews
@Composable
fun TodayWeatherListPreview(){
    WeatherCheckTheme {
        TodayWeatherList()
    }
}