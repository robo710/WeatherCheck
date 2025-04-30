package com.sonchan.weathercheck.presentation.component.weather

import androidx.compose.runtime.Composable
import com.sonchan.weathercheck.domain.model.SkyInfo
import com.sonchan.weathercheck.presentation.component.preview.DarkThemeDevicePreviews
import com.sonchan.weathercheck.presentation.component.preview.DevicePreviews
import com.sonchan.weathercheck.ui.theme.WeatherCheckTheme

@Composable
fun WeatherSummaryCard(
    date: String,
    temp: Int,
    pop: Int,
    sky: SkyInfo,
    maxTemp: Int,
    minTemp: Int,
    humidity: Int,
){

}

//@DevicePreviews
//@DarkThemeDevicePreviews
//@Composable
//fun WeatherSummaryCardPreview(){
//    WeatherCheckTheme {
//        WeatherSummaryCard()
//    }
//}