package com.devautro.coursesapp.feature_favourites.domain.repository

import com.devautro.coursesapp.feature_main.domain.model.Course
import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {

    suspend fun updateCourseIsFavourite(course: Course)

    suspend fun getAllFavouritesCourses(): Flow<List<Course>>
}