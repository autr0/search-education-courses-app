package com.devautro.coursesapp.feature_account.presentation.adapter

import com.devautro.coursesapp.feature_account.presentation.model.AccountCourseCard

interface AccountCardActionListener {
    fun onFavouriteClicked(courseCard: AccountCourseCard)
    fun onCourseCardGetId(courseCard: AccountCourseCard)
}