package com.devautro.coursesapp.feature_favourites.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devautro.coursesapp.CourseApp
import com.devautro.coursesapp.R
import com.devautro.coursesapp.databinding.FragmentFavouritesBinding
import com.devautro.coursesapp.feature_favourites.presentation.adapter.FavouritesAdapter
import com.devautro.coursesapp.feature_main.domain.model.Course
import com.devautro.coursesapp.feature_main.presentation.adapter.CourseCardActionListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouritesFragment : Fragment(R.layout.fragment_favourites) {
    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavouritesAdapter
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: FavouritesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity().application as CourseApp).appComponent.injectFavourites(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FavouritesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.favouritesRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = FavouritesAdapter(object : CourseCardActionListener {
            override fun onDetailCLicked(course: Course) {
                navigateToDetail(courseId = course.id)
            }

            override fun onFavouriteClicked(course: Course) {
                viewModel.updateFavouriteCourse(course = course)
                Toast.makeText(context, "Убираем из избранного...", Toast.LENGTH_SHORT).show()
            }

            override fun onCourseCardGetId(course: Course) {
                Toast.makeText(context, course.title, Toast.LENGTH_SHORT).show()
            }

        })
        recyclerView.adapter = adapter

        setUpData()

    }

    private fun setUpData() {
        lifecycleScope.launch {
            viewModel.courses.collectLatest { uiState ->
                adapter.submitList(uiState.favouritesCoursesList)
            }
        }
    }

    private fun navigateToDetail(courseId: Long) {
        val action = FavouritesFragmentDirections.actionFavouritesFragmentToDetailFragment(courseId)
        findNavController().navigate(action)
    }
}