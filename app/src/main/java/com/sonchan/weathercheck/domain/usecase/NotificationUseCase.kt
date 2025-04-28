package com.sonchan.weathercheck.domain.usecase

import android.content.Context
import com.sonchan.weathercheck.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationUseCase @Inject constructor(
    private val repository: NotificationRepository
){
    fun showNotification(context: Context){
        repository.showNotification(context)
    }
}