package com.devautro.coursesapp.feature_main.data.remote

import com.devautro.coursesapp.feature_main.data.remote.model.AuthorResponse
import com.devautro.coursesapp.feature_main.data.remote.model.CourseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface StepickApi {

    @GET("courses")
    suspend fun getCourses(
        @Query("page") page: Int
    ): CourseResponse

    @GET("users")
    suspend fun getAuthors(
        @Query("ids[]") ids: List<Long>
    ): AuthorResponse

    companion object {
        const val BASE_URL = "https://stepik.org/api/"
    }
}