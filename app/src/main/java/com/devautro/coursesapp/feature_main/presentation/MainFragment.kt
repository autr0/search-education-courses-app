package com.devautro.coursesapp.feature_main.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devautro.coursesapp.CourseApp
import com.devautro.coursesapp.R
import com.devautro.coursesapp.databinding.FragmentMainBinding
import com.devautro.coursesapp.feature_main.domain.model.Course
import com.devautro.coursesapp.feature_main.presentation.adapter.CourseCardActionListener
import com.devautro.coursesapp.feature_main.presentation.adapter.MainAdapter
import com.devautro.coursesapp.feature_main.presentation.adapter.MainLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MainAdapter
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity().application as CourseApp).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.filterButton.setOnClickListener { filterButton ->
            PopupMenu(requireContext(), filterButton).apply {
                inflate(R.menu.filter_menu)
                setOnMenuItemClickListener { item ->
                    Toast.makeText(requireContext(), item.title, Toast.LENGTH_SHORT).show()
                    true
                }
            }.show()
        }

        recyclerView = binding.mainRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = MainAdapter(object : CourseCardActionListener {
            override fun onDetailCLicked(course: Course) {
                navigateToDetail(courseId = course.id)
//                Toast.makeText(context, "Navigate to detail screen", Toast.LENGTH_SHORT).show()
            }

            override fun onFavouriteClicked(course: Course) {
                Toast.makeText(context, "Favourite changed", Toast.LENGTH_SHORT).show()
            }

            override fun onCourseCardGetId(course: Course) {
                Toast.makeText(context, course.title, Toast.LENGTH_SHORT).show()
            }

        })
        recyclerView.adapter = adapter.withLoadStateFooter(
            footer = MainLoadStateAdapter({}) // do it later
        )
        lifecycleScope.launch {
            viewModel.coursePagingFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun navigateToDetail(courseId: Long) {
        val action = MainFragmentDirections.actionMainFragmentToDetailFragment(longValue = courseId)
        findNavController().navigate(action)
    }
}