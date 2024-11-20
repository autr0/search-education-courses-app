package com.devautro.coursesapp.feature_course_detail.data.repository

import com.devautro.coursesapp.feature_course_detail.domain.repository.CourseDetailRepository
import com.devautro.coursesapp.feature_main.data.local.CourseDao
import com.devautro.coursesapp.feature_main.data.mappers.toCourse
import com.devautro.coursesapp.feature_main.domain.model.Course
import javax.inject.Inject

class CourseDetailRepositoryImpl @Inject constructor(
    private val dao: CourseDao
) : CourseDetailRepository {

    override suspend fun getCourseById(id: Long): Course {
        return dao.getCourseById(courseId = id).toCourse()
    }
}