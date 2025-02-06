package com.app.githubu.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView;
import com.app.githubu.databinding.ItemRepoBinding
import com.app.githubu.model.content.UserRepo
import com.app.githubu.utils.setSafeOnClickListener

class UserRepoPagingAdapter : PagingDataAdapter<UserRepo, UserRepoPagingAdapter.UserRepoViewHolder>(UserRepoDiffCallback) {

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

    object UserRepoDiffCallback : DiffUtil.ItemCallback<UserRepo>() {
        override fun areItemsTheSame(oldItem: UserRepo, newItem: UserRepo) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UserRepo, newItem: UserRepo) = oldItem == newItem

        override fun getChangePayload(oldItem: UserRepo, newItem: UserRepo): Any? {
            if (oldItem != newItem) {
                return newItem
            }
            return super.getChangePayload(oldItem, newItem)
        }
    }

    inner class UserRepoViewHolder(private val binding: ItemRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: UserRepo) {
            binding.tvName.text = data.name
            binding.tvDesc.text = data.description

            binding.root.setSafeOnClickListener {
                onItemClickCallback?.onClicked(data)
            }
        }
    }

    fun setOnItemClickCallback(adapterCallback: UserRepoAdapterCallback) {
        this.onItemClickCallback = adapterCallback
    }

    interface UserRepoAdapterCallback {
        fun onClicked(userRepo: UserRepo)
    }
}