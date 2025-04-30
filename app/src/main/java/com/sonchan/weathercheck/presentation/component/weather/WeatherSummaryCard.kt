package com.sonchan.weathercheck.presentation.component.weather

import androidx.compose.runtime.Composable
import com.sonchan.weathercheck.presentation.component.preview.DarkThemeDevicePreviews
import com.sonchan.weathercheck.presentation.component.preview.DevicePreviews
import com.sonchan.weathercheck.ui.theme.WeatherCheckTheme

@Composable
fun WeatherSummaryCard(){

}

@DevicePreviews
@DarkThemeDevicePreviews
@Composable
fun WeatherSummaryCardPreview(){
    WeatherCheckTheme {
        WeatherSummaryCard()
    }
}