package com.devautro.coursesapp.feature_main.domain.repository

import androidx.paging.PagingData
import com.devautro.coursesapp.feature_main.domain.model.Course
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun updateCourseIsFavourite(course: Course, newIsFavourite: Boolean)

    suspend fun getPagingData(): Flow<PagingData<Course>>

}