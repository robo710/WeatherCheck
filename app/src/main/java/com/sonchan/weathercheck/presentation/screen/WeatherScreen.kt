package com.sonchan.weathercheck.presentation.screen

import android.graphics.drawable.Drawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.sonchan.weathercheck.R
import com.sonchan.weathercheck.presentation.component.preview.DarkThemeDevicePreviews
import com.sonchan.weathercheck.presentation.component.preview.DevicePreviews
import com.sonchan.weathercheck.presentation.viewmodel.WeatherViewModel
import com.sonchan.weathercheck.ui.theme.WeatherCheckTheme

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = hiltViewModel()
){
    val weatherInfo by viewModel.weatherInfo.collectAsState()
    val context = LocalContext.current

    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (weatherInfo != null) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "최고 기온: ${weatherInfo!!.maxTemp}°C",
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "최저 기온: ${weatherInfo!!.minTemp}°C",
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "강수 확률: ${weatherInfo!!.precipitation}%",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            } else {
                CircularProgressIndicator()
            }
            Button(
                onClick = {
                    viewModel.getNotification(
                        context = context,
                        icon = R.drawable.ic_launcher_foreground,
                        title = "테스트",
                        text = "입니다."
                    )
                }
            ){}
        }
    }
}

@DevicePreviews
@DarkThemeDevicePreviews
@Composable
fun WeatherScreenPreview(){
    WeatherCheckTheme {
        WeatherScreen()
    }
}