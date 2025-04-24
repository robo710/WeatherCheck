package com.sonchan.weathercheck.data.repository

import com.sonchan.weathercheck.data.remote.api.WeatherApi
import com.sonchan.weathercheck.data.remote.model.WeatherResponse
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val apiService: WeatherApi
){
    suspend fun getWeatherForecast(
        serviceKey: String,
        dataType: String,
        numOfRows: Int = 1000,
        pageNo: Int = 1,
        baseDate: String,
        baseTime: String,
        nx: Int,
        ny: Int
    ): WeatherResponse {
        return apiService.getWeatherForecast(
            serviceKey = serviceKey,
            dataType = dataType,
            numOfRows = numOfRows,
            pageNo = pageNo,
            baseDate = baseDate,
            baseTime = baseTime,
            nx = nx,
            ny = ny
        )
    }
}