package com.devautro.coursesapp.feature_course_detail.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.devautro.coursesapp.CourseApp
import com.devautro.coursesapp.R
import com.devautro.coursesapp.databinding.FragmentCourseDetailBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class CourseDetailFragment : Fragment(R.layout.fragment_course_detail) {
    private lateinit var binding: FragmentCourseDetailBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: CourseDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity().application as CourseApp).appComponent.injectDetail(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CourseDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCourseDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val courseId = arguments?.getLong("longValue")
        courseId?.let { viewModel.loadCourseById(courseId = it) }

        setUpData()


        binding.detailBackButton.setOnClickListener {
            navigateBack()
        }
    }

    private fun setUpData() {
        lifecycleScope.launch {
            viewModel.courseInfo.collectLatest { course ->
                with(binding) {
                    detailFavouritesButton.setOnClickListener {
                        viewModel.updateFavouriteCourse(course = course, newIsFavourite = !course.isFavourite)
                    }
                    val favouriteIcon = if (course.isFavourite) R.drawable.favourite_filled_icon else R.drawable.favourites_tab
                    detailCourseDate.text = course.updateDate
                    detailCourseHeader.text = course.title
                    courseDescription.text = course.summary
                    detailCourseRating.text = course.reviewSummary
                    detailFavouritesButton.setImageResource(favouriteIcon)
                    if (!course.cover.isNullOrBlank()) {
                        Glide.with(detailCourseImage.context)
                            .load(course.cover)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .placeholder(R.drawable.search_icon)
                            .error(R.drawable.search_icon)
                            .into(detailCourseImage)
                    } else {
                        Glide.with(detailCourseImage.context)
                            .load(R.drawable.search_icon)
                            .into(detailCourseImage)
                    }
                    authorName.text = course.authorName
                    if (!course.authorImage.isNullOrBlank()) {
                        Glide.with(authorIcon.context)
                            .load(course.authorImage)
                            .override(64)
                            .circleCrop()
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .placeholder(R.drawable.search_icon)
                            .error(R.drawable.search_icon)
                            .into(authorIcon)
                    } else {
                        Glide.with(authorIcon.context)
                            .load(R.drawable.search_icon)
                            .into(authorIcon)
                    }

                    startCourseButton.setOnClickListener {
                        course.continueUrl?.let { url ->
                            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://stepik.org$url"))
                            startActivity(browserIntent)
                        }
                    }
                }
            }
        }
    }

    private fun navigateBack() {
        findNavController().navigateUp()
    }
}