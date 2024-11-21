package com.devautro.coursesapp.feature_main.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    val courseId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)
