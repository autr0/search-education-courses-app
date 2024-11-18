package com.devautro.coursesapp.feature_main.data.remote.model

import com.google.gson.annotations.SerializedName

data class AuthorResponse(
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("users")
    val authors: List<Author>
)
