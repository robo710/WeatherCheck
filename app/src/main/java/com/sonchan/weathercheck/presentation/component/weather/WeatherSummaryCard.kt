package com.sonchan.weathercheck.presentation.component.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sonchan.weathercheck.presentation.component.preview.DarkThemeDevicePreviews
import com.sonchan.weathercheck.presentation.component.preview.DevicePreviews
import com.sonchan.weathercheck.ui.theme.WeatherCheckTheme

@Composable
fun WeatherSummaryCard(
    modifier: Modifier = Modifier,
    date: String,
    temp: Int,
    pop: Int,
    sky: String,
    maxTemp: Int,
    minTemp: Int,
    humidity: Int,
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {

    }
}

@DevicePreviews
@DarkThemeDevicePreviews
@Composable
fun WeatherSummaryCardPreview(){
    WeatherCheckTheme {
        WeatherSummaryCard(
            date = "20250430",
            temp = 23,
            pop = 10,
            sky = "맑음",
            maxTemp = 25,
            minTemp = 18,
            humidity = 45
        )
    }
}