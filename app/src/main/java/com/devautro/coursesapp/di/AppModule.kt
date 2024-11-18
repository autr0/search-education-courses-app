package com.devautro.coursesapp.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.devautro.coursesapp.feature_main.data.local.CourseDatabase
import com.devautro.coursesapp.feature_main.data.local.CourseEntity
import com.devautro.coursesapp.feature_main.data.remote.CourseRemoteMediator
import com.devautro.coursesapp.feature_main.data.remote.StepickApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule {

    @Singleton
    @Provides
    fun providesCourseDatabase(context: Context): CourseDatabase {
        return Room.databaseBuilder(
            context,
            CourseDatabase::class.java,
            "course.db"
        ).build()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Singleton
    @Provides
    fun providesPager(db: CourseDatabase, api: StepickApi): Pager<Int, CourseEntity> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = CourseRemoteMediator(
                db = db,
                api = api
            ),
            pagingSourceFactory = { db.dao.pagingSource() }
        )
    }
}
