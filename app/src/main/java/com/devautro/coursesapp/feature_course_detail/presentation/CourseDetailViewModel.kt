package com.devautro.coursesapp.feature_course_detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devautro.coursesapp.feature_course_detail.domain.repository.CourseDetailRepository
import com.devautro.coursesapp.feature_main.domain.model.Course
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class CourseDetailViewModel @Inject constructor(
    private val repository: CourseDetailRepository
) : ViewModel() {

    private val _courseInfo = MutableStateFlow(Course(id = 0L, title = "Unknown Course", summary = "We don't know anything about it..."))
    val courseInfo: StateFlow<Course> = _courseInfo.asStateFlow()

    fun loadCourseById(courseId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCourseById(id = courseId)?.let { course ->
                _courseInfo.update { state ->
                    state.copy(
                        id = course.id,
                        title = course.title,
                        description = course.description,
                        cover = course.cover,
                        summary = course.summary,
                        language = course.language,
                        courseType = course.courseType,
                        difficulty = course.difficulty,
                        isFavourite = course.isFavourite,
                        isPopular = course.isPopular,
                        beginDate = course.beginDate,
                        updateDate = course.updateDate,
                        continueUrl = course.continueUrl,
                        authorId = course.authorId,
                        authorName = course.authorName,
                        authorImage = course.authorImage,
                        reviewSummary = course.reviewSummary,
                        price = course.price
                    )
                }
            }
        }
    }

    fun updateFavouriteCourse(course: Course, newIsFavourite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCourseIsFavourite(course = course.copy(isFavourite = newIsFavourite), newIsFavourite = newIsFavourite)
            loadCourseById(courseId = course.id)
        }
    }

}