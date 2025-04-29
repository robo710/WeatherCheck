package com.sonchan.weathercheck.data.remote.api

import com.sonchan.weathercheck.BuildConfig
import com.sonchan.weathercheck.data.remote.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("getVilageFcst")
    suspend fun getWeatherForecast(
        @Query("serviceKey") serviceKey: String = BuildConfig.WEATHER_API_KEY,
        @Query("numOfRows") numOfRows: Int = 1500,
        @Query("pageNo") pageNo: Int = 1,
        @Query("dataType") dataType: String = "JSON",
        @Query("base_time") baseTime: String,
        @Query("base_date") baseDate: String,
        @Query("nx") nx: Int,
        @Query("ny") ny: Int
    ): WeatherResponse
}