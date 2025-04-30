package com.sonchan.weathercheck.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sonchan.weathercheck.R
import com.sonchan.weathercheck.domain.model.TodayWeatherItem
import com.sonchan.weathercheck.domain.model.WeatherInfo
import com.sonchan.weathercheck.presentation.component.preview.DarkThemeDevicePreviews
import com.sonchan.weathercheck.presentation.component.preview.DevicePreviews
import com.sonchan.weathercheck.presentation.component.weather.TodayWeatherList
import com.sonchan.weathercheck.presentation.viewmodel.WeatherViewModel
import com.sonchan.weathercheck.ui.theme.WeatherCheckTheme

@Composable
fun WeatherScreenRoute(
    viewModel: WeatherViewModel = hiltViewModel()
){
    val weatherInfo by viewModel.weatherInfo.collectAsState()
    val today by viewModel.today.collectAsState()
    val context = LocalContext.current

    val hourlyWeatherList = weatherInfo?.temps?.get(today)?.mapNotNull { (time, temp) ->
        val pop = weatherInfo!!.precipitation[today]?.get(time)
        val sky = weatherInfo!!.sky[today]?.get(time)
        if (pop != null && sky != null) {
            TodayWeatherItem(time, temp, pop, sky)
        } else null
    } ?: emptyList()

    WeatherScreen(
        weatherInfo = weatherInfo,
        onNotificationClick = {viewModel.getNotification(
            context = context,
            icon = R.drawable.ic_launcher_foreground,
            title = "WeatherCheck",
            text = "최고 기온: ${weatherInfo!!.maxTemp}°C, 최저 기온: ${weatherInfo!!.minTemp}°C"
        )},
        todayWeatherDataList = hourlyWeatherList
    )
}

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    weatherInfo: WeatherInfo?,
    onNotificationClick: () -> Unit,
    todayWeatherDataList: List<TodayWeatherItem>
){
    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        if (weatherInfo != null) {
            Text(
                text = "최고 기온: ${weatherInfo!!.maxTemp}°C",
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "최저 기온: ${weatherInfo!!.minTemp}°C",
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "오늘의 날씨",
                color = MaterialTheme.colorScheme.primary
            )
            LazyRow(
                modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(todayWeatherDataList) { item ->
                    TodayWeatherList(
                        time = item.time,
                        temp = item.temp,
                        pop = item.pop,
                        skyIcon = item.sky.icon,
                        skyDescription = item.sky.description,
                    )
                }
            }
        } else {
            CircularProgressIndicator()
        }
        Button(
            onClick = {
                onNotificationClick()
            }
        ){}

    }
}

@DevicePreviews
@DarkThemeDevicePreviews
@Composable
fun WeatherScreenPreview(){
    WeatherCheckTheme {
        WeatherScreen(
            weatherInfo = null,
            onNotificationClick = {},
            todayWeatherDataList = emptyList()
        )
    }
}