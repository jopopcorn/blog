package com.example.blog.data

data class Post(
    var id: Int,
    var userId: Int,
    var title: String,
    var content: String,
    var image: ArrayList<String>,
    var numberOfLike: Int,
    var numberOfComment: Int,
    var date: String,
    var isPressedLike: Boolean
) {
    constructor(): this(
        id = 0,
        userId = 0,
        title = "",
        content = "",
        image = arrayListOf(),
        0,
        0,
        date = "",
        isPressedLike = false
    )

    constructor(id: Int, userId: Int): this(
        id = id,
        userId = userId,
        title = "",
        content = "",
        image = arrayListOf(),
        0,
        0,
        date = "",
        isPressedLike = false
    )

    constructor(id: Int, userId: Int, title: String, content: String, date: String) : this(
        id = id,
        userId = userId,
        title = title,
        content = content,
        image = arrayListOf(),
        0,
        0,
        date = date,
        isPressedLike = false
    )
}
