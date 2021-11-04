package com.example.blog

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.blog.data.Comment
import com.example.blog.data.Post
import com.example.blog.ui.home.HomeAdapter
import com.example.blog.ui.post_detail.comment.CommentAdapter

@BindingAdapter("postList")
fun bindPostList(recyclerView: RecyclerView, data: List<Post>?){
    val adapter = recyclerView.adapter as HomeAdapter
    adapter.submitList(data)
}

@BindingAdapter("commentList")
fun bindCommentList(recyclerView: RecyclerView, data: List<Comment>?){
    val adapter = recyclerView.adapter as CommentAdapter
    adapter.submitList(data)
}