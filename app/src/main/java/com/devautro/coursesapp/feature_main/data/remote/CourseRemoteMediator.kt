package com.devautro.coursesapp.feature_main.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.devautro.coursesapp.feature_main.data.local.CourseDatabase
import com.devautro.coursesapp.feature_main.data.local.CourseEntity
import com.devautro.coursesapp.feature_main.data.mappers.toCourseEntity
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class CourseRemoteMediator(
    private val db: CourseDatabase,
    private val api: StepickApi
) : RemoteMediator<Int, CourseEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CourseEntity>
    ): MediatorResult {
        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        lastItem.page?.let { it + 1 } ?: 1
                    }
                }
            }
            val courseResponse = api.getCourses(page = loadKey)
            val courses = courseResponse.courses

            db.withTransaction {     // excecute all or none
                if (loadType == LoadType.REFRESH) {
                    db.dao.clearAllBesideFavourites()
                }
                val courseEntities = courses.map { it.toCourseEntity(page = courseResponse.meta.page) }
                db.dao.upsertAll(courseEntities)
            }
            MediatorResult.Success(
                endOfPaginationReached = courses.isEmpty() //api returns empty list if page doesn't exist
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}