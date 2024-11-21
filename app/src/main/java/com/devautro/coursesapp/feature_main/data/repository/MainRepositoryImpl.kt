package com.devautro.coursesapp.feature_main.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import com.devautro.coursesapp.feature_favourites.data.mappers.toFavouriteCourseEntity
import com.devautro.coursesapp.feature_main.data.local.CourseDatabase
import com.devautro.coursesapp.feature_main.data.mappers.toCourse
import com.devautro.coursesapp.feature_main.data.mappers.toCourseEntity
import com.devautro.coursesapp.feature_main.data.remote.CourseRemoteMediator
import com.devautro.coursesapp.feature_main.data.remote.StepickApi
import com.devautro.coursesapp.feature_main.domain.model.Course
import com.devautro.coursesapp.feature_main.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val db: CourseDatabase,
    private val api: StepickApi
) : MainRepository {

    override suspend fun updateCourseIsFavourite(course: Course, newIsFavourite: Boolean) {
        try {
            db.dao.updateUserIsFavorite(
                courseId = course.id,
                isFavourite = newIsFavourite
            )
            if (newIsFavourite) {
                db.favouritesDao.insertToFavourite(course = course.toCourseEntity().toFavouriteCourseEntity())
            } else {
                db.favouritesDao.deleteFavouriteCourse(course = course.toCourseEntity().toFavouriteCourseEntity())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getPagingData(): Flow<PagingData<Course>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = CourseRemoteMediator(
                db = db,
                api = api
            ),
            pagingSourceFactory = { db.dao.pagingSource() }
        )

        return pager.flow
            .map { pagingData -> pagingData.filter { it.title != "empty" }.map { it.toCourse() } }
    }
}