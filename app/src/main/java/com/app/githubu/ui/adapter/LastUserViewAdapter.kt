package com.app.githubu.ui.adapter;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.githubu.databinding.ItemLastUserViewedBinding
import com.app.githubu.model.entities.LastViewUser
import com.app.githubu.utils.setSafeOnClickListener

class LastUserViewAdapter constructor(private val list: List<LastViewUser>) :
    RecyclerView.Adapter<LastUserViewAdapter.LastUserViewAdapterHolder>() {

    private var onItemClickCallback: LastUserViewAdapterCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastUserViewAdapterHolder {
        val binding = ItemLastUserViewedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return LastUserViewAdapterHolder(binding)
    }

    override fun onBindViewHolder(holder: LastUserViewAdapterHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun setOnItemClickCallback(adapterCallback: LastUserViewAdapterCallback) {
        this.onItemClickCallback = adapterCallback
    }

    inner class LastUserViewAdapterHolder(val binding: ItemLastUserViewedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: LastViewUser) {
            binding.tvUsername.text = item.username

            binding.root.setSafeOnClickListener {
                onItemClickCallback?.onLastUserViewAdapterClicked(
                    item
                )
            }
        }
    }

    interface LastUserViewAdapterCallback {
        fun onLastUserViewAdapterClicked(item: LastViewUser)
    }
}