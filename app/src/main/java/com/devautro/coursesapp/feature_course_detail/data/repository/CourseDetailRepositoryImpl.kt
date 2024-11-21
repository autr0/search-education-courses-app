package com.devautro.coursesapp.feature_course_detail.data.repository

import com.devautro.coursesapp.feature_course_detail.domain.repository.CourseDetailRepository
import com.devautro.coursesapp.feature_favourites.data.local.FavouriteCourseDao
import com.devautro.coursesapp.feature_favourites.data.mappers.toFavouriteCourseEntity
import com.devautro.coursesapp.feature_main.data.local.CourseDao
import com.devautro.coursesapp.feature_main.data.mappers.toCourse
import com.devautro.coursesapp.feature_main.data.mappers.toCourseEntity
import com.devautro.coursesapp.feature_main.domain.model.Course
import javax.inject.Inject

class CourseDetailRepositoryImpl @Inject constructor(
    private val courseDao: CourseDao,
    private val favouritesDao: FavouriteCourseDao
) : CourseDetailRepository {

    override suspend fun getCourseById(id: Long): Course? {
        return courseDao.getCourseById(courseId = id)?.toCourse()
    }

    override suspend fun updateCourseIsFavourite(course: Course, newIsFavourite: Boolean) {
        // update isFavourite flag on the favourites screen
        if (newIsFavourite) {
            favouritesDao.insertToFavourite(course = course.toCourseEntity().toFavouriteCourseEntity())
        } else {
            favouritesDao.deleteFavouriteCourse(course = course.toCourseEntity().toFavouriteCourseEntity())
        }
        // update isFavourite flag on the main screen
        courseDao.updateUserIsFavorite(courseId = course.id, isFavourite = newIsFavourite)
    }
}