package com.devautro.coursesapp.feature_account.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devautro.coursesapp.R
import com.devautro.coursesapp.databinding.FragmentAccountBinding
import com.devautro.coursesapp.databinding.FragmentFavouritesBinding
import com.devautro.coursesapp.feature_account.presentation.adapter.AccountAdapter
import com.devautro.coursesapp.feature_account.presentation.adapter.AccountCardActionListener
import com.devautro.coursesapp.feature_account.presentation.model.AccountCourseCard
import com.devautro.coursesapp.feature_favourites.presentation.adapter.FavouritesAdapter
import com.devautro.coursesapp.feature_main.presentation.adapter.CourseCardActionListener
import com.devautro.coursesapp.feature_main.presentation.model.CourseCard

class AccountFragment : Fragment(R.layout.fragment_account) {
    private lateinit var binding: FragmentAccountBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AccountAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.favouritesRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = AccountAdapter(object : AccountCardActionListener {
            override fun onFavouriteClicked(courseCard: AccountCourseCard) {
                Toast.makeText(context, "Favourite changed", Toast.LENGTH_SHORT).show()
            }

            override fun onCourseCardGetId(courseCard: AccountCourseCard) {
                Toast.makeText(context, courseCard.title, Toast.LENGTH_SHORT).show()

            }

        })
        adapter.submitList(
            listOf(
                AccountCourseCard(
                    id = 0L,
                    title = "Java course for Beginners",
                    completedLessons = 22,
                    totalLessons = 44
                ),
                AccountCourseCard(
                    id = 1L,
                    title = "Kotlin course for Beginners",
                    isFavourite = true,
                    completedLessons = 22,
                    totalLessons = 66
                ),
                AccountCourseCard(
                    id = 2L,
                    title = "Java course for Beginners",
                    completedLessons = 31,
                    totalLessons = 52
                ),
                AccountCourseCard(
                    id = 3L,
                    title = "Kotlin course for Beginners",
                    isFavourite = true,
                    completedLessons = 7,
                    totalLessons = 39
                )
            )
        )
        recyclerView.adapter = adapter

    }

}