package com.devautro.coursesapp.feature_main.presentation

import androidx.paging.PagingData
import com.devautro.coursesapp.feature_main.domain.model.Course

data class MainUiState(
    val pagingData: PagingData<Course> = PagingData.empty()
)
