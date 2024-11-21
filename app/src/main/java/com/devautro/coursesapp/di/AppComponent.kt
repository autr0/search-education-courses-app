package com.devautro.coursesapp.di

import android.content.Context
import com.devautro.coursesapp.feature_course_detail.presentation.CourseDetailFragment
import com.devautro.coursesapp.feature_favourites.presentation.FavouritesFragment
import com.devautro.coursesapp.feature_main.presentation.MainFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class ,NetworkModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(fragment: MainFragment)
    fun injectDetail(fragment: CourseDetailFragment)
    fun injectFavourites(fragment: FavouritesFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}