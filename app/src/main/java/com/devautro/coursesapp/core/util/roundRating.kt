package com.devautro.coursesapp.core.util

import java.text.DecimalFormat

fun Float.roundRating(): Float {
    val decimalFormat = DecimalFormat("#.#")
    return decimalFormat.format(this).replace(",", ".").toFloat()
}

fun Float.formatRating(): String {
    val (beforePoint, afterPoint) = this.toString().split(".")
    return if (afterPoint == "0") beforePoint else this.toString()
}

fun String.reFormatRating(): Float {
    return if (this.contains(".")) this.toFloat() else "$this.0".toFloat()
}

