package com.devautro.coursesapp.feature_main.data.remote.model.review_summaries

import com.devautro.coursesapp.feature_main.data.remote.model.Meta
import com.google.gson.annotations.SerializedName

data class CourseReviewSummariesResponse(
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("course-review-summaries")
    val courseReviewSummariesDto: List<CourseReviewSummariesDto>
)
