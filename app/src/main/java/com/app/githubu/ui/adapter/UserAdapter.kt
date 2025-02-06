package com.app.githubu.ui.adapter;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.githubu.databinding.ItemUserBinding
import com.app.githubu.model.content.User
import com.app.githubu.utils.image.loadUrlCirle
import com.app.githubu.utils.setSafeOnClickListener

class UserAdapter constructor(private val list: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.UserSearchAdapterHolder>() {

    private var onItemClickCallback: UserSearchAdapterCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserSearchAdapterHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return UserSearchAdapterHolder(binding)
    }

    override fun onBindViewHolder(holder: UserSearchAdapterHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun setOnItemClickCallback(adapterCallback: UserSearchAdapterCallback) {
        this.onItemClickCallback = adapterCallback
    }

    inner class UserSearchAdapterHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: User) {
            binding.ivAvatar.loadUrlCirle(item.avatarUrl)
            binding.tvUsername.text = item.username

            binding.root.setSafeOnClickListener {
                onItemClickCallback?.onClicked(item)
            }
        }
    }

    interface UserSearchAdapterCallback {
        fun onClicked(user: User)
    }
}