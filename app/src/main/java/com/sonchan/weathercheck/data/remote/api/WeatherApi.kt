package com.sonchan.weathercheck.data.remote.api

import com.sonchan.weathercheck.BuildConfig
import com.sonchan.weathercheck.data.remote.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("getVilageFcst")
    suspend fun getWeatherForecast(
        @Query("serviceKey") serviceKey: String = BuildConfig.WEATHER_API_KEY,
        @Query("dataType") dataType: String = "JSON",
        @Query("numOfRows") numOfRows: Int,
        @Query("pageNo") pageNo: Int,
        @Query("base_date") baseDate: Int,
        @Query("base_time") baseTime: Int,
        @Query("nx") nx: Int,
        @Query("ny") ny: Int
    ): WeatherResponse
}