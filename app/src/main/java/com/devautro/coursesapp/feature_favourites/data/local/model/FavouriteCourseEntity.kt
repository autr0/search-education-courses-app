package com.devautro.coursesapp.feature_favourites.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_courses")
data class FavouriteCourseEntity(
    @PrimaryKey
    val courseId: Long,
    val title: String? = null,
    val description: String? = null,
    val cover: String? = null,
    val summary: String? = null,
    val language: String? = null,
    val courseType: String? = null,
    val difficulty: String? = null,
    val isFavourite: Boolean = false,
    val isPopular: Boolean = false,
    val beginDate: String? = null,
    val updateDate: String? = null,
    val continueUrl: String? = null,
    val authorId: Long? = null,
    val authorName: String? = null,
    val authorImage: String? = null,
    val reviewSummary: Float = 0F,
    val price: String? = null
)
