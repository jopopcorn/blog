package com.example.blog.ui.post_detail.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.blog.data.Comment
import com.example.blog.databinding.ItemCommentBinding
import com.example.blog.util.DiffUtilCallback

class CommentAdapter(private val onClick: (Comment) -> Unit): ListAdapter<Comment, CommentAdapter.ViewHolder>(DiffUtilCallback()) {
    inner class ViewHolder(private val binding: ItemCommentBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Comment){
            binding.apply {
                comment = data
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCommentBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }
}