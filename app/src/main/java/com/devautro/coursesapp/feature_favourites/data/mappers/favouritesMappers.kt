package com.devautro.coursesapp.feature_favourites.data.mappers

import com.devautro.coursesapp.core.util.formatDate
import com.devautro.coursesapp.core.util.formatPrice
import com.devautro.coursesapp.core.util.formatRating
import com.devautro.coursesapp.core.util.reFormatDate
import com.devautro.coursesapp.core.util.reFormatPrice
import com.devautro.coursesapp.core.util.reFormatRating
import com.devautro.coursesapp.feature_favourites.data.local.model.FavouriteCourseEntity
import com.devautro.coursesapp.feature_main.data.local.CourseEntity
import com.devautro.coursesapp.feature_main.domain.model.Course

fun FavouriteCourseEntity.toCourseEntity(): CourseEntity {
    return CourseEntity(
        courseId = courseId,
        title = title,
        description = description,
        cover = cover,
        summary = summary,
        language = language,
        courseType = courseType,
        difficulty = difficulty,
        isPopular = isPopular,
        isFavourite = isFavourite,
        beginDate = beginDate,
        updateDate = updateDate,
        continueUrl = continueUrl,
        authorId = authorId,
        authorName = authorName,
        authorImage = authorImage,
        reviewSummary = reviewSummary,
        price = price
    )
}

fun CourseEntity.toFavouriteCourseEntity(): FavouriteCourseEntity {
    return FavouriteCourseEntity(
        courseId = courseId,
        title = title,
        description = description,
        cover = cover,
        summary = summary,
        language = language,
        courseType = courseType,
        difficulty = difficulty,
        isPopular = isPopular,
        isFavourite = isFavourite,
        beginDate = beginDate,
        updateDate = updateDate,
        continueUrl = continueUrl,
        authorId = authorId,
        authorName = authorName,
        authorImage = authorImage,
        reviewSummary = reviewSummary,
        price = price
    )
}

fun FavouriteCourseEntity.toCourse(): Course {
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
        isFavourite = isFavourite,
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

fun Course.toFavouriteCourseEntity(): FavouriteCourseEntity {
    return FavouriteCourseEntity(
        courseId = id,
        title = title,
        description = description,
        cover = cover,
        summary = summary,
        language = language,
        courseType = courseType,
        difficulty = difficulty,
        isPopular = isPopular,
        isFavourite = isFavourite,
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