package com.app.githubu.ui.adapter;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.githubu.databinding.ItemUserBinding
import com.app.githubu.model.content.User
import com.app.githubu.utils.image.loadUrlCirle
import com.app.githubu.utils.setSafeOnClickListener

class UserPagingAdapter : PagingDataAdapter<User, UserPagingAdapter.UserViewHolder>(DiffCallback) {

    private var onItemClickCallback: UserAdapterCallback? = null

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val data = getItem(position)
        data?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    object DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: User) {
            binding.ivAvatar.loadUrlCirle(data.avatarUrl)
            binding.tvUsername.text = data.username

            binding.root.setSafeOnClickListener {
                onItemClickCallback?.onClicked(data)
            }
        }
    }

    fun setOnItemClickCallback(adapterCallback: UserAdapterCallback) {
        this.onItemClickCallback = adapterCallback
    }

    interface UserAdapterCallback {
        fun onClicked(data: User)
    }
}