package com.example.weatherapp.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun timeConverter(time: String): String {
        val outputFormat: DateFormat = SimpleDateFormat("HH 'h'", Locale.getDefault())
        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

        val date: Date = inputFormat.parse(time)!!
        return outputFormat.format(date)
    }
}