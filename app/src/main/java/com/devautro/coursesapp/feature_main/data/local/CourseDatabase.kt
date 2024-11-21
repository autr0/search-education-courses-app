package com.devautro.coursesapp.feature_main.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devautro.coursesapp.feature_favourites.data.local.FavouriteCourseDao
import com.devautro.coursesapp.feature_favourites.data.local.model.FavouriteCourseEntity

@Database(
    entities = [CourseEntity::class, RemoteKeys::class, FavouriteCourseEntity::class],
    version = 1
)
abstract class CourseDatabase : RoomDatabase() {

    abstract val dao: CourseDao
    abstract val keysDao: RemoteKeysDao
    abstract val favouritesDao: FavouriteCourseDao
}