package com.example.blog.data

data class User(
    val id: Int,
    var nickname: String,
    var profileImageUrl: String,
    var blog: Blog
) {
    constructor(id: Int) : this(id = id, "user$id", "", Blog(id))
}