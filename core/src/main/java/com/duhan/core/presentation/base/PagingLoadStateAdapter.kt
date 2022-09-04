package com.duhan.core.presentation.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.duhan.core.R
import com.duhan.core.databinding.ItemServiceStateBinding

class PagingLoadStateAdapter<T : Any, VH : RecyclerView.ViewHolder>(
    private val adapter: PagingDataAdapter<T, VH>
) : LoadStateAdapter<PagingLoadStateAdapter.ItemServiceStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        ItemServiceStateViewHolder(
            ItemServiceStateBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_service_state, parent, false)
            )
        ) { adapter.retry() }

    override fun onBindViewHolder(holder: ItemServiceStateViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    class ItemServiceStateViewHolder(
        private val binding: ItemServiceStateBinding,
        private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retryCallback() }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                progressBar.isVisible = loadState is LoadState.Loading
                retryButton.isVisible = loadState is LoadState.Error
                errorMessage.isVisible =
                    !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
                errorMessage.text = (loadState as? LoadState.Error)?.error?.message
            }
        }
    }
}
