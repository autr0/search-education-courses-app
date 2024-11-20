package com.devautro.coursesapp.core.util

fun String?.formatPrice(): String {
    if (this == null) return "Бесплатно"

    val (beforePoint, afterPoint) = this.split(".")
    return if (afterPoint == "00") "$beforePoint ₽" else "$this ₽"
}

fun String.reFormatPrice(): String? {
    if (this == "Бесплатно") return null

    val stringWithoutSign = this.replace(" ₽", "")
    return if (!stringWithoutSign.contains(".")) "$stringWithoutSign.00" else stringWithoutSign
}