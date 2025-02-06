package com.app.githubu.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView;
import com.app.githubu.databinding.ItemRepoBinding
import com.app.githubu.model.content.UserRepo

class UserRepoPagingAdapter : PagingDataAdapter<UserRepo, UserRepoPagingAdapter.UserRepoViewHolder>(DiffCallback) {

    private var onItemClickCallback: UserRepoAdapterCallback? = null

    override fun onBindViewHolder(holder: UserRepoViewHolder, position: Int) {
        val data = getItem(position)
        data?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserRepoViewHolder {
        val binding =
            ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserRepoViewHolder(binding)
    }

    object DiffCallback : DiffUtil.ItemCallback<UserRepo>() {
        override fun areItemsTheSame(oldItem: UserRepo, newItem: UserRepo) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UserRepo, newItem: UserRepo) = oldItem == newItem
    }

    inner class UserRepoViewHolder(private val binding: ItemRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: UserRepo) {
            binding.tvName.text = data.name
            binding.tvDesc.text = data.description
        }
    }

    fun setOnItemClickCallback(adapterCallback: UserRepoAdapterCallback) {
        this.onItemClickCallback = adapterCallback
    }

    interface UserRepoAdapterCallback {
        fun onClicked()
    }
}