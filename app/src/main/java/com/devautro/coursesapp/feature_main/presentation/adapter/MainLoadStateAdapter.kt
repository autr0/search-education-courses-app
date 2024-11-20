package com.devautro.coursesapp.feature_main.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devautro.coursesapp.R
import com.devautro.coursesapp.databinding.LoadStateItemBinding

class MainLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<MainLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: MainLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MainLoadStateViewHolder {
        return MainLoadStateViewHolder.create(parent, retry)
    }


}

class MainLoadStateViewHolder(
    private val binding: LoadStateItemBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
        binding.errorMsg.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): MainLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.load_state_item , parent, false)
            val binding = LoadStateItemBinding.bind(view)
            return MainLoadStateViewHolder(binding, retry)
        }
    }

}
