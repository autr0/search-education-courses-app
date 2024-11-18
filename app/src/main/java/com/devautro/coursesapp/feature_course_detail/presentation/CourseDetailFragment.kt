package com.devautro.coursesapp.feature_course_detail.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.devautro.coursesapp.R
import com.devautro.coursesapp.databinding.FragmentCourseDetailBinding

class CourseDetailFragment : Fragment(R.layout.fragment_course_detail) {
    private lateinit var binding: FragmentCourseDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCourseDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* TODO Retrieve data from viewModel to UI here --> */

        binding.detailBackButton.setOnClickListener {
            navigateBack()
        }
    }

    private fun navigateBack() {
        findNavController().navigateUp()
    }
}