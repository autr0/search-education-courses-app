package com.devautro.coursesapp.feature_account.presentation.model

data class AccountCourseCard(
    val id: Long,
    val image: String = "",
    val isFavourite: Boolean = false,
    val rating: String = "4.9",
    val date: String = "12 may 2024",
    val title :String,
    val progress: String = "50%",
    val completedLessons: Int,
    val totalLessons: Int
)
