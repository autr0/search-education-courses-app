package com.devautro.coursesapp.feature_main.data.remote

import com.devautro.coursesapp.feature_main.data.remote.model.course_author.AuthorResponse
import com.devautro.coursesapp.feature_main.data.remote.model.CourseResponse
import com.devautro.coursesapp.feature_main.data.remote.model.review_summaries.CourseReviewSummariesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface StepickApi {

    @GET("courses")
    suspend fun getCourses(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): CourseResponse

    @GET("course-review-summaries")
    suspend fun getCourseSummaryById(
        @Query("ids") ids: List<Long>
    ): CourseReviewSummariesResponse

    @GET("users")
    suspend fun getAuthorsById(
        @Query("ids[]") ids: List<Long>
    ): AuthorResponse

    companion object {
        const val BASE_URL = "https://stepik.org/api/"
    }
}