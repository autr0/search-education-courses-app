package com.devautro.coursesapp.feature_favourites.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devautro.coursesapp.R
import com.devautro.coursesapp.databinding.MainCourseItemBinding
import com.devautro.coursesapp.feature_main.domain.model.Course
import com.devautro.coursesapp.feature_main.presentation.adapter.CourseCardActionListener

class FavouritesAdapter(
    private val courseCardActionListener: CourseCardActionListener
) : RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder>(), View.OnClickListener {

    private val differCallback = object : DiffUtil.ItemCallback<Course>() {
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    fun submitList(list: List<Course>) { differ.submitList(list) } // add new items via this method

    inner class FavouritesViewHolder(val binding: MainCourseItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MainCourseItemBinding.inflate(inflater, parent, false)

        binding.courseMore.setOnClickListener(this)
        binding.favouritesButton.setOnClickListener(this)

        return FavouritesViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        val course = differ.currentList[position]
        val favouriteIcon = if (course.isFavorite) R.drawable.favourite_filled_icon else R.drawable.favourites_tab

        with(holder.binding) {
            favouritesButton.tag = course
            courseMore.tag = course

            courseHeader.text = course.title
            courseBody.text = course.description
            courseDate.text = course.updateDate
            courseRating.text = "?"
            coursePrice.text = course.price
            favouritesButton.setImageResource(favouriteIcon)

//          MAYBE DIFFERENT IMAGE LOAD -> FIND OUT HOW TO CACHE IT !!! -->
            if (!course.cover.isNullOrBlank()) {
                Glide.with(courseImage.context)
                    .load(course.cover)
                    .centerCrop()
                    .placeholder(R.drawable.search_icon) // define placeholder for zero data!!
                    .error(R.drawable.search_icon)
                    .into(courseImage)
            } else {
                Glide.with(courseImage.context)
                    .load(R.drawable.search_icon)
                    .into(courseImage)
            }
        }

    }

    override fun onClick(view: View?) {
        val courseCard = view?.tag as Course

        when(view.id) {
            R.id.course_more -> courseCardActionListener.onDetailCLicked(courseCard)
            R.id.favourites_button -> courseCardActionListener.onFavouriteClicked(courseCard)
            else -> courseCardActionListener.onCourseCardGetId(courseCard)
        }
    }

}