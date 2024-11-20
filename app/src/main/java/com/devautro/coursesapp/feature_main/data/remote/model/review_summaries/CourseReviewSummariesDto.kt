package com.devautro.coursesapp.feature_main.data.remote.model.review_summaries

import com.google.gson.annotations.SerializedName

data class CourseReviewSummariesDto(
    @SerializedName("id")
    val courseSummaryId: Long,
    @SerializedName("course")
    val courseId: Long,
    @SerializedName("average")
    val rating: Float
)
