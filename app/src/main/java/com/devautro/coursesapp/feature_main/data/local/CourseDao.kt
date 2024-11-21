package com.devautro.coursesapp.feature_main.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(courses: List<CourseEntity>)

    @Query("SELECT * FROM course_table")
    fun pagingSource(): PagingSource<Int, CourseEntity>

    @Query("SELECT * FROM course_table WHERE courseId = :courseId")
    suspend fun getCourseById(courseId: Long): CourseEntity?

    @Query("UPDATE course_table SET isFavourite = :isFavourite WHERE courseId = :courseId")
    suspend fun updateUserIsFavorite(courseId: Long, isFavourite: Boolean)

    @Query("SELECT * FROM course_table WHERE isFavourite = 1")
    fun getAllFavouritesCourses(): Flow<List<CourseEntity>>

    @Query("SELECT courseId FROM course_table WHERE isFavourite = 1")
    fun getAllFavouritesCoursesIds(): List<Long>

    @Query("DELETE FROM course_table")
    suspend fun clearAll()
}