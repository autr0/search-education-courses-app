package com.devautro.coursesapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devautro.coursesapp.core.presentation.ViewModelFactory
import com.devautro.coursesapp.core.presentation.ViewModelKey
import com.devautro.coursesapp.feature_course_detail.presentation.CourseDetailViewModel
import com.devautro.coursesapp.feature_main.presentation.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CourseDetailViewModel::class)
    fun bindCourseDetailViewModel(viewModel: CourseDetailViewModel): ViewModel
}