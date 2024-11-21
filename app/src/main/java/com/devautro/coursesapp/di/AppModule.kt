package com.devautro.coursesapp.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.devautro.coursesapp.feature_course_detail.data.repository.CourseDetailRepositoryImpl
import com.devautro.coursesapp.feature_course_detail.domain.repository.CourseDetailRepository
import com.devautro.coursesapp.feature_favourites.data.repository.FavouritesRepositoryImpl
import com.devautro.coursesapp.feature_favourites.domain.repository.FavouritesRepository
import com.devautro.coursesapp.feature_main.data.local.CourseDatabase
import com.devautro.coursesapp.feature_main.data.local.CourseEntity
import com.devautro.coursesapp.feature_main.data.remote.CourseRemoteMediator
import com.devautro.coursesapp.feature_main.data.remote.StepickApi
import com.devautro.coursesapp.feature_main.data.repository.MainRepositoryImpl
import com.devautro.coursesapp.feature_main.domain.repository.MainRepository
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

//    @OptIn(ExperimentalPagingApi::class)
//    @Singleton
//    @Provides
//    fun providesPager(db: CourseDatabase, api: StepickApi): Pager<Int, CourseEntity> {
//        return Pager(
//            config = PagingConfig(pageSize = 10),
//            remoteMediator = CourseRemoteMediator(
//                db = db,
//                api = api
//            ),
//            pagingSourceFactory = { db.dao.pagingSource() }
//        )
//    }

    @Singleton
    @Provides
    fun providesCourseDetailRepository(db: CourseDatabase): CourseDetailRepository {
        return CourseDetailRepositoryImpl(courseDao = db.dao, favouritesDao = db.favouritesDao)
    }

    @Singleton
    @Provides
    fun providesMainRepository(db: CourseDatabase, api: StepickApi): MainRepository {
        return MainRepositoryImpl(db = db, api = api)
    }

    @Singleton
    @Provides
    fun providesFavouritesRepository(db: CourseDatabase): FavouritesRepository {
        return FavouritesRepositoryImpl(courseDao = db.dao, favouritesDao = db.favouritesDao)
    }
}
