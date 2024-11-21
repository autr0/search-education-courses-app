package com.devautro.coursesapp.feature_favourites.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devautro.coursesapp.feature_favourites.data.local.model.FavouriteCourseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteCourseDao {
    @Query("SELECT * FROM favourite_courses")
    fun getAllFavouritesCourses(): Flow<List<FavouriteCourseEntity>>

    @Query("SELECT courseId FROM favourite_courses WHERE isFavourite = 1")
    suspend fun getAllFavouriteCourseIds(): List<Long>

    @Query("SELECT * FROM favourite_courses WHERE courseId = :courseId")
    suspend fun getCourseById(courseId: Long): FavouriteCourseEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToFavourite(course: FavouriteCourseEntity)

    @Delete
    suspend fun deleteFavouriteCourse(course: FavouriteCourseEntity)
}