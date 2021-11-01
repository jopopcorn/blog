package com.example.blog.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.blog.data.Post
import com.example.blog.databinding.ItemHomePostListBinding
import com.example.blog.util.DiffUtilCallback

class HomeAdapter(private val onClick: (Post) -> Unit) :
    ListAdapter<Post, HomeAdapter.ViewHolder>(DiffUtilCallback()) {

    inner class ViewHolder(private val binding: ItemHomePostListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Post) {
            binding.apply {
                post = data
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemHomePostListBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

}