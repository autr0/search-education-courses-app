package com.devautro.coursesapp.feature_main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.devautro.coursesapp.feature_main.data.local.CourseEntity
import com.devautro.coursesapp.feature_main.data.mappers.toCourse
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MainViewModel @Inject constructor(
    pager: Pager<Int, CourseEntity>
) : ViewModel() {

    val coursePagingFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { it.toCourse() }
        }
        .cachedIn(viewModelScope)
}
