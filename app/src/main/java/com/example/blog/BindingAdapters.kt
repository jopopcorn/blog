package com.example.blog

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.blog.data.Post
import com.example.blog.ui.home.HomeAdapter

@BindingAdapter("postList")
fun bindPostList(recyclerView: RecyclerView, data: List<Post>?){
    val adapter = recyclerView.adapter as HomeAdapter
    adapter.submitList(data)
}