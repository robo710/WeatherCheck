package com.sonchan.weathercheck.data.preference

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val ALARM_PREFERENCES_NAME = "alarm_preferences"
private val Context.alarmDataStore by preferencesDataStore(name = ALARM_PREFERENCES_NAME)

class AlarmPreference(private val context: Context) {
    private val HOUR_KEY = intPreferencesKey("alarm_hour")
    private val MINUTE_KEY = intPreferencesKey("alarm_minute")

    val alarmHour: Flow<Int> = context.alarmDataStore.data.map { it[HOUR_KEY] ?: 8 }
    val alarmMinute: Flow<Int> = context.alarmDataStore.data.map { it[MINUTE_KEY] ?: 0 }

    suspend fun saveAlarmTime(hour: Int, minute: Int) {
        Log.d("로그", "Saving hour=$hour, minute=$minute")
        context.alarmDataStore.edit { preferences ->
            preferences[HOUR_KEY] = hour
            preferences[MINUTE_KEY] = minute
        }
    }
}