package com.devautro.coursesapp.feature_course_detail.domain.repository

import com.devautro.coursesapp.feature_main.domain.model.Course

interface CourseDetailRepository {

    suspend fun getCourseById(id: Long): Course

}