package com.devautro.coursesapp.feature_main.data.mappers

import com.devautro.coursesapp.core.util.formatDate
import com.devautro.coursesapp.core.util.formatPrice
import com.devautro.coursesapp.core.util.formatRating
import com.devautro.coursesapp.core.util.reFormatDate
import com.devautro.coursesapp.core.util.reFormatPrice
import com.devautro.coursesapp.core.util.reFormatRating
import com.devautro.coursesapp.core.util.roundRating
import com.devautro.coursesapp.feature_main.data.local.CourseEntity
import com.devautro.coursesapp.feature_main.data.remote.model.CourseDto
import com.devautro.coursesapp.feature_main.data.remote.model.course_author.Author
import com.devautro.coursesapp.feature_main.domain.model.Course

fun CourseDto.toCourseEntity(rating: Float = 0F, author: Author): CourseEntity {
    return CourseEntity(
        courseId = id,
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
        authorId = author.id,
        authorName = author.fullName,
        authorImage = author.avatar,
        reviewSummary = rating.roundRating(), // round 'till one sign after point
        price = price
    )
}

fun CourseEntity.toCourseDto(): CourseDto {

    return CourseDto(
        id = courseId,
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
        authors = authorId?.let { listOf(it) } ,
        reviewSummary = reviewSummary.toLong(),
        price = price
    )
}

fun CourseEntity.toCourse(): Course {
    return Course(
        id = courseId,
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
        updateDate = updateDate?.let { formatDate(it) },
        continueUrl = continueUrl,
        authorId = authorId,
        authorName = authorName,
        authorImage = authorImage,
        reviewSummary = reviewSummary.formatRating(),
        price = price.formatPrice()
    )
}

fun Course.toCourseEntity(): CourseEntity {
    return CourseEntity(
        courseId = id,
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
        updateDate = updateDate?.let { reFormatDate(it) },
        continueUrl = continueUrl,
        authorId = authorId,
        authorName = authorName,
        authorImage = authorImage,
        reviewSummary = reviewSummary?.reFormatRating() ?: 0F,
        price = price?.reFormatPrice()
    )
}