package com.example.blog.data

data class Comment(
    var id: Int,
    var postId: Int,
    var userId: Int,
    var nickname: String,
    var profileImageUrl: String,
    var date: String,
    var content: String
){
    constructor(): this(
        id = 0,
        postId = 0,
        userId = 0,
        nickname = "",
        profileImageUrl = "",
        date = "",
        content = ""
    )

    constructor(id: Int, postId: Int, userId: Int, nickname: String, date: String, content: String): this(
        id = id,
        postId = postId,
        userId = userId,
        nickname = nickname,
        profileImageUrl = "",
        date = date,
        content = content
    )
}
