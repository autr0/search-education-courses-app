package com.devautro.coursesapp.feature_main.data.remote.model

import com.google.gson.annotations.SerializedName

data class CourseResponse(
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("courses")
    val courses: List<CourseDto>
)