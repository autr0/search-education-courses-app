package com.devautro.coursesapp.feature_favourites.presentation

import com.devautro.coursesapp.feature_main.domain.model.Course

data class FavouritesUiState(
    val favouritesCoursesList: List<Course> = emptyList()
)
