package com.devautro.coursesapp.feature_main.data.remote.model.course_author

import com.devautro.coursesapp.feature_main.data.remote.model.Meta
import com.google.gson.annotations.SerializedName

data class AuthorResponse(
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("users")
    val authorDtos: List<AuthorDto>
)
