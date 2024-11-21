package com.devautro.coursesapp.feature_favourites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devautro.coursesapp.feature_favourites.domain.repository.FavouritesRepository
import com.devautro.coursesapp.feature_main.domain.model.Course
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouritesViewModel @Inject constructor(
    private val repository: FavouritesRepository
) : ViewModel() {

    private val _courses = MutableStateFlow(FavouritesUiState())
    val courses: StateFlow<FavouritesUiState> = _courses.asStateFlow()

    init {
        getAllFavouritesCourses()
    }

    fun updateFavouriteCourse(course: Course) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCourseIsFavourite(course = course)
        }
    }

    private fun getAllFavouritesCourses() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllFavouritesCourses().collect { courseList ->
                _courses.update { state ->
                    state.copy(
                        favouritesCoursesList = courseList
                    )
                }
            }
        }
    }

}