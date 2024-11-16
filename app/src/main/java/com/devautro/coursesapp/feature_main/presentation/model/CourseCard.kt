package com.devautro.coursesapp.feature_main.presentation.model

data class CourseCard(
    val id: Long,
    val image: String = "",
    val isFavourite: Boolean = false,
    val rating: String = "4.9",
    val date: String = "12 may 2024",
    val title :String,
    val description: String,
    val price: String
)
