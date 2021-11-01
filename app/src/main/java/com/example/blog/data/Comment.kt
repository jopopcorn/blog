package com.example.blog.data

data class Comment(
    val id: Int,
    val userId: Int,
    var nickname: String,
    val profileImageUrl: String,
    val date: String,
    var content: String
)
