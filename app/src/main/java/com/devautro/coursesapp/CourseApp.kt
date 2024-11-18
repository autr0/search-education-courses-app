package com.devautro.coursesapp

import android.app.Application
import com.devautro.coursesapp.di.AppComponent
import com.devautro.coursesapp.di.DaggerAppComponent

class CourseApp : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}