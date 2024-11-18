package com.devautro.coursesapp.feature_main.data.remote.model

import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("id")
    val id: Long,
    @SerializedName("full_name")
    val fullName: String? = null,
    @SerializedName("avatar")
    val avatar: String? = null  // image url
)
