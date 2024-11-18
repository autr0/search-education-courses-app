package com.devautro.coursesapp.feature_main.data.remote.model

import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("page")
    val page: Int,
    @SerializedName("has_next")
    val hasNext: Boolean,
    @SerializedName("has_previous")
    var hasPrevious: Boolean
)
