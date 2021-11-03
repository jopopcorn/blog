package com.example.blog.data

data class User(
    var id: Int,
    var nickname: String,
    var profileImageUrl: String
) {
    constructor(): this(id = 0, nickname = "", profileImageUrl = "")

    constructor(id: Int) : this(id = id, "user$id", "")
}