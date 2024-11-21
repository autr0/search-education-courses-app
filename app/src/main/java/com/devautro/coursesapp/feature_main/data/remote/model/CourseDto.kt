package com.devautro.coursesapp.feature_main.data.remote.model

import com.google.gson.annotations.SerializedName

data class CourseDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("cover")
    val cover: String? = null,
    @SerializedName("summary")
    val summary: String? = null, // in case of description is empty
    @SerializedName("language")
    val language: String? = null, // don't know if I need it ?
    @SerializedName("course_type")
    val courseType: String? = null,  // need it for filtering?
    @SerializedName("difficulty")
    val difficulty: String? = null,  // need it for filtering?
    @SerializedName("is_favorite")
    val isFavourite: Boolean = false,               // need it!
    @SerializedName("is_popular")
    val isPopular: Boolean = false,                // need it!
    @SerializedName("begin_date")
    val beginDate: String? = null,
    @SerializedName("update_date")
    val updateDate: String? = null,     // date to display in the list screen

    @SerializedName("continue_url")
    val continueUrl: String? = null,

    @SerializedName("authors")
    val authors: List<Long>? = null,   // list of id's for certain authors
    @SerializedName("review_summary")
    val reviewSummary: Long = 0,            // id to api call
    @SerializedName("price")
    val price: String? = null,
)
