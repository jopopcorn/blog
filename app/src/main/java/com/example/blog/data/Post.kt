package com.example.blog.data

data class Post(
    val id: Int,
    var title: String,
    var content: String,
    var image: ArrayList<String>,
    var numberOfLike: Int,
    var numberOfComment: Int,
    val date: String,
    var commentList: ArrayList<Comment>
)
