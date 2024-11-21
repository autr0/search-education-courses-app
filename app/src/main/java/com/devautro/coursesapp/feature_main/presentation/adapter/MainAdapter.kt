package com.devautro.coursesapp.feature_main.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.devautro.coursesapp.R
import com.devautro.coursesapp.databinding.MainCourseItemBinding
import com.devautro.coursesapp.feature_main.domain.model.Course

class MainAdapter(
    private val courseCardActionListener: CourseCardActionListener
) : PagingDataAdapter<Course, MainAdapter.MainViewHolder>(differCallback), View.OnClickListener {

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<Course>() {
            override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class MainViewHolder(val binding: MainCourseItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MainCourseItemBinding.inflate(inflater, parent, false)

        binding.courseMore.setOnClickListener(this)
        binding.favouritesButton.setOnClickListener(this)

        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val course = getItem(position)

        course?.let {
            val favouriteIcon = if (course.isFavourite) R.drawable.favourite_filled_icon else R.drawable.favourites_tab
            with(holder.binding) {
                favouritesButton.tag = course
                courseMore.tag = course

                courseHeader.text = course.title
                courseBody.text = course.summary // description is with html/ css code ...
                courseDate.text = course.updateDate
                courseRating.text = course.reviewSummary.toString()
                coursePrice.text = course.price
                favouritesButton.setImageResource(favouriteIcon)
                if (!course.cover.isNullOrBlank()) {
                    Glide.with(courseImage.context)
                        .load(course.cover)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
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

interface CourseCardActionListener {
    fun onDetailCLicked(course: Course)
    fun onFavouriteClicked(course: Course)
    fun onCourseCardGetId(course: Course)
}