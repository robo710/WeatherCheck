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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sonchan.weathercheck.R
import com.sonchan.weathercheck.domain.model.SkyInfo
import com.sonchan.weathercheck.domain.model.TodayWeatherItem
import com.sonchan.weathercheck.domain.model.WeatherInfo
import com.sonchan.weathercheck.presentation.component.preview.DarkThemeDevicePreviews
import com.sonchan.weathercheck.presentation.component.preview.DevicePreviews
import com.sonchan.weathercheck.presentation.component.weather.TodayWeatherListItem
import com.sonchan.weathercheck.presentation.component.weather.WeatherSummaryCard
import com.sonchan.weathercheck.presentation.viewmodel.WeatherViewModel
import com.sonchan.weathercheck.ui.theme.WeatherCheckTheme

@Composable
fun WeatherScreenRoute(
    viewModel: WeatherViewModel = hiltViewModel()
){
    val weatherInfo by viewModel.weatherInfo.collectAsState()
    val today by viewModel.today.collectAsState()
    val context = LocalContext.current
    val nearestWeather = viewModel.getNearestWeatherItem()

    val hourlyWeatherList = weatherInfo?.temps?.get(today)?.mapNotNull { (time, temp) ->
        val pop = weatherInfo!!.precipitation[today]?.get(time)
        val sky = weatherInfo!!.sky[today]?.get(time)
        val humidity = weatherInfo!!.humidity[today]?.get(time)
        if (pop != null && sky != null && humidity != null) {
            TodayWeatherItem(time, temp, pop, sky, humidity)
        } else null
    } ?: emptyList()

    WeatherScreen(
        weatherInfo = weatherInfo,
        today = today,
        onNotificationClick = {viewModel.getNotification(
            context = context,
            icon = R.drawable.ic_launcher_foreground,
            title = "WeatherCheck",
            text = "최고 기온: ${weatherInfo!!.maxTemp[today]}°C, 최저 기온: ${weatherInfo!!.minTemp[today]}°C"
        )},
        todayWeatherDataList = hourlyWeatherList,
        nearestWeather = nearestWeather
    )
}

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    weatherInfo: WeatherInfo?,
    today: String,
    onNotificationClick: () -> Unit,
    todayWeatherDataList: List<TodayWeatherItem>,
    nearestWeather: TodayWeatherItem?
){
    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        if (weatherInfo != null) {
            WeatherSummaryCard(
                date = today,
                temp = nearestWeather!!.temp,
                pop = nearestWeather.pop,
                skyIcon = nearestWeather.sky.icon,
                skyDescription = nearestWeather.sky.description,
                maxTemp = weatherInfo.maxTemp[today] ?: 0,
                minTemp = weatherInfo.minTemp[today] ?: 0,
                humidity = nearestWeather.humidity
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
                    TodayWeatherListItem(
                        time = item.time,
                        temp = item.temp,
                        pop = item.pop,
                        skyIcon = item.sky.icon,
                        skyDescription = item.sky.description,
                        humidity = item.humidity
                    )
                }
            }
            Button(
                onClick = {
                    onNotificationClick()
                }
            ){}
        } else {
            CircularProgressIndicator()
        }
    }
}

@DevicePreviews
@DarkThemeDevicePreviews
@Composable
fun WeatherScreenPreview(){
    WeatherCheckTheme {
        WeatherScreen(
            weatherInfo = WeatherInfo(
                temps = mapOf("20250430" to mapOf("0200" to 15)),
                maxTemp = mapOf("20250430" to 20),
                minTemp = mapOf("20250430" to 10),
                precipitation = mapOf("20250430" to mapOf("0200" to 10)),
                sky = mapOf("20250430" to mapOf("0200" to SkyInfo("맑음", R.drawable.sunny_icon))),
                humidity = mapOf("20250430" to mapOf("0200" to 50))
            ),
            today = "20250430",
            onNotificationClick = {},
            todayWeatherDataList = listOf(
                TodayWeatherItem(
                    time = "0200",
                    temp = 15,
                    pop = 10,
                    sky = SkyInfo("맑음", R.drawable.sunny_icon),
                    humidity = 50,
                )
            ),
            nearestWeather = TodayWeatherItem(
                time = "0200",
                temp = 0,
                pop = 0,
                sky = SkyInfo("맑음", R.drawable.sunny_icon),
                humidity = 0,
            ),
        )
    }
}