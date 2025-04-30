package com.sonchan.weathercheck.presentation.component.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
fun WeatherSummaryCard(
    modifier: Modifier = Modifier,
    date: String,
    temp: Int,
    pop: Int,
    skyIcon: Int,
    skyDescription: String,
    maxTemp: Int,
    minTemp: Int,
    humidity: Int,
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 24.dp, vertical = 20.dp),
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "서울특별시",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 24.sp
            )
            Text(
                text = "${date.substring(0, 4)}년 ${date.substring(4, 6)}월 ${date.substring(6, 8)}일",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 15.sp
            )
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = modifier
            ) {
                Text(
                    text = "$temp°C",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 48.sp
                )
                Text(
                    text = skyDescription,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 24.sp
                )
                Text(
                    text = "최고 $maxTemp°C / 최저 $minTemp°C",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 24.sp
                )
            }
            Icon(
                painter = painterResource(id = skyIcon),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = skyDescription,
                modifier = modifier
                    .size(96.dp)
            )
        }
        Row (
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ){
            Column(
                modifier = modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(start = 20.dp, end = 40.dp, top = 20.dp, bottom = 20.dp),
            ) {
                Text(
                    text = "습도",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 24.sp
                )
                Text(
                    text = "$humidity%",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 24.sp
                )
            }
            Column(
                modifier = modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(start = 20.dp, end = 40.dp, top = 20.dp, bottom = 20.dp),
            ) {
                Text(
                    text = "강우확률",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 24.sp
                )
                Text(
                    text = "$pop%",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 24.sp
                )
            }
        }
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
            skyIcon = R.drawable.sunny_icon,
            skyDescription = "맑음",
            maxTemp = 25,
            minTemp = 18,
            humidity = 45
        )
    }
}