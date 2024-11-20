package com.devautro.coursesapp.feature_main.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface CourseDao {

    @Upsert
    suspend fun upsertAll(courses: List<CourseEntity>)

    @Query("SELECT * FROM course_table")
    fun pagingSource(): PagingSource<Int, CourseEntity>

    @Query("DELETE FROM course_table WHERE isFavorite = 0") // because we need to display favourites in the certsin screen
    suspend fun clearAllBesideFavourites()

    @Query("SELECT * FROM course_table WHERE courseId = :courseId")
    suspend fun getCourseById(courseId: Long): CourseEntity

    @Query("DELETE FROM course_table")
    suspend fun clearAll()
}