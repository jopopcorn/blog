package com.example.blog.data

data class User(
    val id : String,
    var nickname : String,
    var introduce : String,
    var blog : Blog
)