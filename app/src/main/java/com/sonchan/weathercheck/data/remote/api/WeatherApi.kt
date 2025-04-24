package com.sonchan.weathercheck.data.remote.api

import com.sonchan.weathercheck.data.remote.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("getVilageFcst")
    suspend fun getWeatherForecast(
        @Query("serviceKey") serviceKey: String,
        @Query("dataType") dataType: String = "JSON",
        @Query("numOfRows") numOfRows: Int,
        @Query("pageNo") pageNo: Int,
        @Query("base_date") baseDate: String,
        @Query("base_time") baseTime: String,
        @Query("nx") nx: Int,
        @Query("ny") ny: Int
    ): WeatherResponse
}