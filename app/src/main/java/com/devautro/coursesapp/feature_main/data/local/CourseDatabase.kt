package com.devautro.coursesapp.feature_main.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CourseEntity::class],
    version = 1
)
abstract class CourseDatabase : RoomDatabase() {

    abstract val dao: CourseDao
}