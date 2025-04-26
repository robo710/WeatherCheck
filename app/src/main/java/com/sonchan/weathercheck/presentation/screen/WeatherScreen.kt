package com.sonchan.weathercheck.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sonchan.weathercheck.presentation.component.preview.DevicePreviews
import com.sonchan.weathercheck.ui.theme.WeatherCheckTheme

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier
){

}

@DevicePreviews
@Composable
fun WeatherScreenPreview(){
    WeatherCheckTheme {
        WeatherScreen()
    }
}