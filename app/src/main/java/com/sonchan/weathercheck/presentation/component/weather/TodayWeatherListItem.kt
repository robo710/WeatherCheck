package com.sonchan.weathercheck.presentation.component.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sonchan.weathercheck.R
import com.sonchan.weathercheck.presentation.component.preview.DarkThemeDevicePreviews
import com.sonchan.weathercheck.presentation.component.preview.DevicePreviews
import com.sonchan.weathercheck.ui.theme.WeatherCheckTheme

@Composable
fun TodayWeatherListItem(
    modifier: Modifier = Modifier,
    time: String,
    temp: Int,
    pop: Int,
    skyIcon: Int,
    skyDescription: String,
){
    Column(
        modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color = MaterialTheme.colorScheme.onBackground)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${time.substring(0,2)}시",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 17.sp
        )
        Spacer(modifier.height(10.dp))
        Icon(
            painter = painterResource(id = skyIcon),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = skyDescription,
            modifier = modifier
                .size(48.dp)
        )
        Spacer(modifier.height(10.dp))
        Text(
            text = "$temp°C",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 20.sp
        )
        Text(
            text = "$pop%",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 12.sp
        )
    }
}

@DevicePreviews
@DarkThemeDevicePreviews
@Composable
fun TodayWeatherListItemPreview(){
    WeatherCheckTheme {
        TodayWeatherListItem(
            time = "0200",
            temp = 0,
            pop = 0,
            skyIcon = R.drawable.sunny_icon,
            skyDescription = "맑음"
        )
    }
}