package com.devautro.coursesapp.feature_main.data.remote.model.course_author

import com.google.gson.annotations.SerializedName

data class AuthorDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("full_name")
    val fullName: String? = null,
    @SerializedName("avatar")
    val avatar: String? = null  // image url
)

data class Author(
    val id: Long,
    val fullName: String? = null,
    val avatar: String? = null
)

fun AuthorDto.toAuthor(): Author {
    return Author(
        id = id,
        fullName = fullName,
        avatar = avatar
    )
}

fun Author.toAuthorDto(): AuthorDto {
    return AuthorDto(
        id = id,
        fullName = fullName,
        avatar = avatar
    )
}
