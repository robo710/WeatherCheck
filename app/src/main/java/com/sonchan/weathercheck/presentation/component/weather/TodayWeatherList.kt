package com.sonchan.weathercheck.presentation.component.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.sonchan.weathercheck.presentation.component.preview.DarkThemeDevicePreviews
import com.sonchan.weathercheck.presentation.component.preview.DevicePreviews
import com.sonchan.weathercheck.ui.theme.WeatherCheckTheme

@Composable
fun TodayWeatherList(
    modifier: Modifier = Modifier,
    time: String,
    temp: Int,
    pop: Int
){
    Column(
        modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color = MaterialTheme.colorScheme.onBackground)
            .padding(10.dp)
    ) {
        Text(
            text = "${time.substring(0,2)}시",
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "$temp°C",
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "$pop%",
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@DevicePreviews
@DarkThemeDevicePreviews
@Composable
fun TodayWeatherListPreview(){
    WeatherCheckTheme {
        TodayWeatherList(
            time = "0200",
            temp = 0,
            pop = 0
        )
    }
}