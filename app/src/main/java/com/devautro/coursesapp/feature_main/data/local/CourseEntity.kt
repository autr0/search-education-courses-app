package com.devautro.coursesapp.feature_main.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "course_table")
data class CourseEntity(
    @PrimaryKey(autoGenerate = true)
    val tableId: Long? = null, // local id in db
    val courseId: Long = 0, // courseId
    val title: String? = null,
    val description: String? = null,
    val cover: String? = null,
    val summary: String? = null,
    val language: String? = null,
    val courseType: String? = null,
    val difficulty: String? = null,
    val isFavorite: Boolean = false,
    val isPopular: Boolean = false,
    val beginDate: String? = null,
    val updateDate: String? = null,
    val continueUrl: String? = null,
//    val authors: Author? = null,
    val authorId: Long? = null,
    val authorName: String? = null,
    val authorImage: String? = null,
    val reviewSummary: Float = 0F,
    val price: String? = null
)