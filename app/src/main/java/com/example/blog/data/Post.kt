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
) {
    constructor(id: Int, title: String, content: String, date: String) : this(
        id = id,
        title = title,
        content = content,
        image = arrayListOf(),
        0,
        0,
        date = date,
        commentList = arrayListOf()
    )
}
