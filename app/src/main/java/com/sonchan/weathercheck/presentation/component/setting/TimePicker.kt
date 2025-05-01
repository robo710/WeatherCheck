package com.sonchan.weathercheck.presentation.component.setting

import android.app.TimePickerDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun TimePickerDialog(
    onTimeSelected: (hour: Int, minute: Int) -> Unit
){
    val context = LocalContext.current
    val timePickerDialog = remember {
        TimePickerDialog(
            context,
            { _, hour, minute ->
                onTimeSelected(hour, minute)
            },
            8, 0, true
        )
    }

    Button(onClick = { timePickerDialog.show() }) {
        Text("알림 시간 설정")
    }
}