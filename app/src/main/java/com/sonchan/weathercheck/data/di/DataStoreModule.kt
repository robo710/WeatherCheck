package com.sonchan.weathercheck.data.di

import android.content.Context
import com.sonchan.weathercheck.data.preference.AlarmPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideAlarmPreference(@ApplicationContext context: Context): AlarmPreference {
        return AlarmPreference(context)
    }
}