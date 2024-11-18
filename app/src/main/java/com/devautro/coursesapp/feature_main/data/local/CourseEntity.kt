package com.devautro.coursesapp.feature_main.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devautro.coursesapp.feature_main.data.remote.model.Author

@Entity(tableName = "course_table")
data class CourseEntity(
    @PrimaryKey
    val id: Long,
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
    val authors: Long? = null, // id of single author!
    val reviewSummary: Long = 0,
    val price: String? = null,
    val page: Int? = null
)
