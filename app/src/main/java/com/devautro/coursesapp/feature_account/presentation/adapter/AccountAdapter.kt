package com.devautro.coursesapp.feature_account.presentation.adapter

import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.color
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devautro.coursesapp.R
import com.devautro.coursesapp.databinding.AccountCourseItemBinding
import com.devautro.coursesapp.feature_account.presentation.model.AccountCourseCard
import kotlin.math.roundToInt

class AccountAdapter(
    private val accountCardActionListener: AccountCardActionListener
) : RecyclerView.Adapter<AccountAdapter.AccountViewHolder>(), View.OnClickListener {

    private val differCallback = object : DiffUtil.ItemCallback<AccountCourseCard>() {
        override fun areItemsTheSame(oldItem: AccountCourseCard, newItem: AccountCourseCard): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AccountCourseCard, newItem: AccountCourseCard): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    fun submitList(list: List<AccountCourseCard>) { differ.submitList(list) } // add new items via this method

    inner class AccountViewHolder(val binding: AccountCourseItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AccountCourseItemBinding.inflate(inflater, parent, false)

        binding.favouritesButton.setOnClickListener(this)

        return AccountViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val courseCard = differ.currentList[position]
        val favouriteIcon = if (courseCard.isFavourite) R.drawable.favourite_filled_icon else R.drawable.favourites_tab
        val green = ContextCompat.getColor(holder.itemView.context, R.color.Green)
        val lessonsString = ContextCompat.getString(holder.itemView.context, R.string.lessons)
        val lessonsCountText = SpannableStringBuilder()
            .color(green) { append("${courseCard.completedLessons}") }
            .append("/${courseCard.totalLessons} ")
            .append(lessonsString)
        val progressInt = ((courseCard.completedLessons.toDouble() / courseCard.totalLessons.toDouble()) * 100).roundToInt()
        val progress = "$progressInt%"
        Log.d("MyLog", "Progress: $progressInt")

        with(holder.binding) {
            favouritesButton.tag = courseCard

            courseHeader.text = courseCard.title
            courseDate.text = courseCard.date
            courseRating.text = courseCard.rating
            percentageCompleted.text = progress
            lessonsCount.text = lessonsCountText
            progressBar.progress = progressInt
            favouritesButton.setImageResource(favouriteIcon)

//          MAYBE DIFFERENT IMAGE LOAD -> FIND OUT HOW TO CACHE IT !!! -->
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

    override fun onClick(view: View?) {
        val courseCard = view?.tag as AccountCourseCard

        when(view.id) {
            R.id.favourites_button -> accountCardActionListener.onFavouriteClicked(courseCard)
            else -> accountCardActionListener.onCourseCardGetId(courseCard)
        }
    }

}