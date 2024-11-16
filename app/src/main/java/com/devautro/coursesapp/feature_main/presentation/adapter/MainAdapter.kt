package com.devautro.coursesapp.feature_main.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devautro.coursesapp.R
import com.devautro.coursesapp.databinding.MainCourseItemBinding
import com.devautro.coursesapp.feature_main.presentation.model.CourseCard

class MainAdapter(
    private val courseCardActionListener: CourseCardActionListener
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>(), View.OnClickListener {

    private val differCallback = object : DiffUtil.ItemCallback<CourseCard>() {
        override fun areItemsTheSame(oldItem: CourseCard, newItem: CourseCard): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CourseCard, newItem: CourseCard): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    // add new items via this method
    fun submitList(list: List<CourseCard>) {
        differ.submitList(list)
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
        val courseCard = differ.currentList[position]
        val favouriteIcon = if (courseCard.isFavourite) R.drawable.favourite_filled_icon else R.drawable.favourites_tab
//        val favouriteColor = if (courseCard.isFavourite) R.color.Green else R.color.White
//        val context = holder.itemView.context //-> for Glide later

        with(holder.binding) {
            favouritesButton.tag = courseCard
            courseMore.tag = courseCard

            courseHeader.text = courseCard.title
            courseBody.text = courseCard.description
            courseDate.text = courseCard.date
            courseRating.text = courseCard.rating
            coursePrice.text = courseCard.price
            favouritesButton.setImageResource(favouriteIcon)
            if (courseCard.image.isNotBlank()) {
                Glide.with(courseImage.context)
                    .load(courseCard.image)
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

    override fun getItemCount(): Int = differ.currentList.size

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