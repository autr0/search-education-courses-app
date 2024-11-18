package com.devautro.coursesapp.feature_main.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devautro.coursesapp.R
import com.devautro.coursesapp.databinding.MainCourseItemBinding
import com.devautro.coursesapp.feature_main.domain.model.Course
import com.devautro.coursesapp.feature_main.presentation.model.CourseCard
import kotlinx.coroutines.Dispatchers

class MainAdapter(
    private val courseCardActionListener: CourseCardActionListener
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>(), View.OnClickListener {

    private val differCallback = object : DiffUtil.ItemCallback<Course>() {
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncPagingDataDiffer(
        diffCallback = differCallback,
        updateCallback = object : ListUpdateCallback {
            override fun onInserted(position: Int, count: Int) {
                notifyItemRangeInserted(position, count)
            }

            override fun onRemoved(position: Int, count: Int) {
                notifyItemRangeRemoved(position, count)
            }

            override fun onMoved(fromPosition: Int, toPosition: Int) {
                notifyItemMoved(fromPosition, toPosition)
            }

            override fun onChanged(position: Int, count: Int, payload: Any?) {
                notifyItemRangeChanged(position, count)
            }

        },
        mainDispatcher = Dispatchers.Main,
        workerDispatcher = Dispatchers.Default
    )

    // add new items via this method
    suspend fun submitList(list: PagingData<Course>) {
        differ.submitData(list)
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
        val course = differ.getItem(position)

        course?.let {
            val favouriteIcon =
                if (course.isFavorite) R.drawable.favourite_filled_icon else R.drawable.favourites_tab
            with(holder.binding) {
                favouritesButton.tag = course
                courseMore.tag = course

                courseHeader.text = course.title
                courseBody.text = course.description
                courseDate.text = course.updateDate
                courseRating.text = "?"
                coursePrice.text = course.price
                favouritesButton.setImageResource(favouriteIcon)
                if (!course.cover.isNullOrBlank()) {
                    Glide.with(courseImage.context)
                        .load(course.cover)
                        .circleCrop()
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

    override fun getItemCount(): Int = differ.itemCount

    override fun onClick(view: View?) {
        val courseCard = view?.tag as CourseCard

        when(view.id) {
            R.id.course_more -> courseCardActionListener.onDetailCLicked(courseCard)
            R.id.favourites_button -> courseCardActionListener.onFavouriteClicked(courseCard)
            else -> courseCardActionListener.onCourseCardGetId(courseCard)
        }
    }

}

interface CourseCardActionListener {
    fun onDetailCLicked(courseCard: CourseCard)
    fun onFavouriteClicked(courseCard: CourseCard)
    fun onCourseCardGetId(courseCard: CourseCard)
}