package com.devautro.coursesapp.core.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
    val date: Date? = inputFormat.parse(dateString)

    return date?.let { outputFormat.format(it) } ?: dateString
}

fun reFormatDate(formattedDate: String): String {
    val inputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
    val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())

    return try {
        val date: Date? = inputFormat.parse(formattedDate)
        date?.let { outputFormat.format(it) } ?: formattedDate
    } catch (e: Exception) {
        formattedDate
    }
}