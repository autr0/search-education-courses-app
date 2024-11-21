package com.devautro.coursesapp.feature_favourites.data.repository

import com.devautro.coursesapp.feature_favourites.data.local.FavouriteCourseDao
import com.devautro.coursesapp.feature_favourites.data.mappers.toCourse
import com.devautro.coursesapp.feature_favourites.data.mappers.toCourseEntity
import com.devautro.coursesapp.feature_favourites.data.mappers.toFavouriteCourseEntity
import com.devautro.coursesapp.feature_favourites.domain.repository.FavouritesRepository
import com.devautro.coursesapp.feature_main.data.local.CourseDao
import com.devautro.coursesapp.feature_main.data.mappers.toCourse
import com.devautro.coursesapp.feature_main.data.mappers.toCourseEntity
import com.devautro.coursesapp.feature_main.domain.model.Course
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavouritesRepositoryImpl @Inject constructor(
    private val courseDao: CourseDao,
    private val favouritesDao: FavouriteCourseDao
) : FavouritesRepository {

    override suspend fun updateCourseIsFavourite(course: Course) {
//        if (newIsFavourite) {
//            favouritesDao.insertToFavourite(course = course.copy(isFavourite = true).toCourseEntity().toFavouriteCourseEntity())
//        } else {
//        }
        favouritesDao.deleteFavouriteCourse(course = course.toFavouriteCourseEntity())
        courseDao.updateUserIsFavorite(courseId = course.id, isFavourite = false)
    }

    override suspend fun getAllFavouritesCourses(): Flow<List<Course>> {
//        return courseDao.getAllFavouritesCourses().map { it.map { courseEntity -> courseEntity.toCourse() } }
        return favouritesDao.getAllFavouritesCourses().map { it.map { courseEntity -> courseEntity.toCourse() } }
    }
}