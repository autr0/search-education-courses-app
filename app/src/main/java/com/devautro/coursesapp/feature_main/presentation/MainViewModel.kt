package com.devautro.coursesapp.feature_main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.devautro.coursesapp.feature_main.domain.model.Course
import com.devautro.coursesapp.feature_main.domain.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _coursePagingFlow = MutableStateFlow(MainUiState())
    val coursePagingFlow: StateFlow<MainUiState> = _coursePagingFlow.asStateFlow()

    init {
        getData()
    }

    fun updateIsFavouriteById(course: Course, newIsFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCourseIsFavourite(
                course = course.copy(isFavourite = newIsFavorite),
                newIsFavourite = newIsFavorite
            )
        }
    }

    private fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPagingData().cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                _coursePagingFlow.update { state -> state.copy(pagingData = pagingData) }
            }
        }
    }
}