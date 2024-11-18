package com.devautro.coursesapp.feature_main.data.mappers

import com.devautro.coursesapp.feature_main.data.local.CourseEntity
import com.devautro.coursesapp.feature_main.data.remote.model.CourseDto
import com.devautro.coursesapp.feature_main.domain.model.Course

fun CourseDto.toCourseEntity(page: Int): CourseEntity {

    return CourseEntity(
        id = id,
        title = title,
        description = description,
        cover = cover,
        summary = summary,
        language = language,
        courseType = courseType,
        difficulty = difficulty,
        isPopular = isPopular,
        isFavorite = isFavorite,
        beginDate = beginDate,
        updateDate = updateDate,
        continueUrl = continueUrl,
        authors = authors?.firstOrNull(),
        reviewSummary = reviewSummary,
        price = price,
        page = page
    )
}

fun CourseEntity.toCourseDto(): CourseDto {
    val author = if (authors != null) listOf(authors) else null

    return CourseDto(
        id = id,
        title = title,
        description = description,
        cover = cover,
        summary = summary,
        language = language,
        courseType = courseType,
        difficulty = difficulty,
        isPopular = isPopular,
        isFavorite = isFavorite,
        beginDate = beginDate,
        updateDate = updateDate,
        continueUrl = continueUrl,
        authors = author,
        reviewSummary = reviewSummary,
        price = price
    )
}

fun CourseEntity.toCourse(): Course {
    return Course(
        id = id,
        title = title,
        description = description,
        cover = cover,
        summary = summary,
        language = language,
        courseType = courseType,
        difficulty = difficulty,
        isPopular = isPopular,
        isFavorite = isFavorite,
        beginDate = beginDate,
        updateDate = updateDate,
        continueUrl = continueUrl,
        authors = authors,
        reviewSummary = reviewSummary,
        price = price
    )
}