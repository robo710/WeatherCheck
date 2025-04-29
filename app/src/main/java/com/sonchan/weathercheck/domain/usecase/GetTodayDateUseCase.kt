package com.sonchan.weathercheck.domain.usecase

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class GetTodayDateUseCase @Inject constructor(

){
    operator fun invoke(): String{
        val today = System.currentTimeMillis()
        val formattedDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date(today))
        return formattedDate.toString()
    }
}