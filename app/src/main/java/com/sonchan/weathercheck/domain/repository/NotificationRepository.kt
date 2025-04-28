package com.sonchan.weathercheck.domain.repository

import android.content.Context

interface NotificationRepository {
    fun showNotification(context: Context)
}