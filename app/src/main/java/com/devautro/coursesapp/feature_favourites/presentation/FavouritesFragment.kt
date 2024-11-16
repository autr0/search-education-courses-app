package com.devautro.coursesapp.feature_favourites.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devautro.coursesapp.R
import com.devautro.coursesapp.databinding.FragmentFavouritesBinding
import com.devautro.coursesapp.feature_favourites.presentation.adapter.FavouritesAdapter
import com.devautro.coursesapp.feature_main.presentation.adapter.CourseCardActionListener
import com.devautro.coursesapp.feature_main.presentation.model.CourseCard

class FavouritesFragment : Fragment(R.layout.fragment_favourites) {
    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavouritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouritesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.favouritesRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = FavouritesAdapter(object : CourseCardActionListener {
            override fun onDetailCLicked(courseCard: CourseCard) {
                navigateToDetail()
//                Toast.makeText(context, "Navigate to detail screen", Toast.LENGTH_SHORT).show()
            }

            override fun onFavouriteClicked(courseCard: CourseCard) {
                Toast.makeText(context, "Favourite changed", Toast.LENGTH_SHORT).show()
            }

            override fun onCourseCardGetId(courseCard: CourseCard) {
                Toast.makeText(context, courseCard.title, Toast.LENGTH_SHORT).show()
            }

        })
        adapter.submitList(
            listOf(
                CourseCard(
                    id = 0L,
                    title = "Java course for Beginners",
                    isFavourite = true,
                    description = "This course is suitable for those people who know computer science basica and wouls like to improve their skills",
                    price = "1 200 $"
                ),
                CourseCard(
                    id = 1L,
                    title = "Kotlin course for Beginners",
                    isFavourite = true,
                    description = "This course is suitable for those people who know computer science basica and wouls like to improve their skills",
                    price = "999 $"
                ),
                CourseCard(
                    id = 2L,
                    title = "Java course for Beginners",
                    isFavourite = true,
                    description = "This course is suitable for those people who know computer science basica and wouls like to improve their skills",
                    price = "1 200 $"
                ),
                CourseCard(
                    id = 3L,
                    title = "Kotlin course for Beginners",
                    isFavourite = true,
                    description = "This course is suitable for those people who know computer science basica and wouls like to improve their skills",
                    price = "999 $"
                )
            )
        )
        recyclerView.adapter = adapter
    }

    private fun navigateToDetail() {
        findNavController().navigate(R.id.detailFragment)
    }
}