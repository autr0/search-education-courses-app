package com.devautro.coursesapp.feature_main.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.devautro.coursesapp.feature_main.data.local.CourseDatabase
import com.devautro.coursesapp.feature_main.data.local.CourseEntity
import com.devautro.coursesapp.feature_main.data.local.RemoteKeys
import com.devautro.coursesapp.feature_main.data.mappers.toCourseEntity
import com.devautro.coursesapp.feature_main.data.remote.model.CourseDto
import com.devautro.coursesapp.feature_main.data.remote.model.course_author.Author
import com.devautro.coursesapp.feature_main.data.remote.model.course_author.toAuthor
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
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state = state)
                    remoteKeys?.nextKey?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state = state)
                    val prevKey = remoteKeys?.prevKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    prevKey
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state = state)
                    val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    nextKey
                }
            }
            val (meta, courses) = api.getCourses(page = page, perPage = 10)
            val prevKey = if (meta.hasPrevious) meta.page - 1 else null
            val nextKey = if (meta.hasNext) meta.page + 1 else null

            val endOfPagination = !meta.hasNext

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.keysDao.clearRemoteKeys()
                    db.dao.clearAll()
                }

                val courseEntities = if (courses.isEmpty() && meta.hasNext) {
                    listOf(CourseEntity(title = "empty"))
                } else {
                    mapCoursesToCoursesWithRating(courses)
                }
                val keys = courseEntities.map {
                    RemoteKeys(
                        courseId = it.courseId,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                db.keysDao.insertAll(keys)
                db.dao.upsertAll(courseEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = endOfPagination
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun mapCoursesToCoursesWithRating(
        coursesDto: List<CourseDto>
    ): List<CourseEntity> {
        val favouriteCoursesIds = db.favouritesDao.getAllFavouriteCourseIds() // to prevent duplicates showing
        val courses = coursesDto.map { courseDto ->
            if (courseDto.id in favouriteCoursesIds) courseDto.copy(isFavourite = true) else courseDto
        }

        val courseReviewIds = courses.map { it.reviewSummary }
        val (_, ratingList) = api.getCourseSummaryById(courseReviewIds)

        val courseAuthorIds = courses.mapNotNull { it.authors?.firstOrNull() }
        val (_, authorsDtoList) = api.getAuthorsById(courseAuthorIds)
        val authorsList = authorsDtoList.map { it.toAuthor() }

        val res = courses.map { courseDto ->
            val courseRating = ratingList.find { it.courseId == courseDto.id }?.rating
            val courseAuthor = authorsList.find { courseDto.authors?.firstOrNull() == it.id }
            courseDto.toCourseEntity(
                rating = courseRating ?: 0F,
                author = courseAuthor ?: Author(id = 1, fullName = "Unknown")
            )
        }
        return res

    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CourseEntity>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { courseEntity ->
                db.keysDao.remoteKeysCourseId(courseEntity.courseId)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, CourseEntity>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { courseEntity ->
                db.keysDao.remoteKeysCourseId(courseEntity.courseId)

            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, CourseEntity>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.courseId?.let { courseId ->
                db.keysDao.remoteKeysCourseId(courseId)
            }
        }
    }

}