package com.kkeb.weatherappkotlincompose.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getFormattedDateTime(dt: Number, pattern: String = "dd-MMM-yyyy"): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(Date(dt.toLong() * 1000))
}

fun getIconUrl(icon: String): String {
    return "https://openweathermap.org/img/wn/$icon@2x.png"
}