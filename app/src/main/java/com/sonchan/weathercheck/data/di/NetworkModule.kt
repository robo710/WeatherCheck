package com.sonchan.weathercheck.data.di

import android.content.Context
import com.sonchan.weathercheck.data.preference.AlarmPreference
import com.sonchan.weathercheck.data.remote.api.WeatherApi
import com.sonchan.weathercheck.data.repository.NotificationRepositoryImpl
import com.sonchan.weathercheck.data.repository.WeatherRepositoryImpl
import com.sonchan.weathercheck.domain.repository.NotificationRepository
import com.sonchan.weathercheck.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // 추가!!
            .build()
        return retrofit
    }

    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherApi): WeatherRepository{
        return WeatherRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideNotificationRepository(): NotificationRepository{
        return NotificationRepositoryImpl()
    }
}