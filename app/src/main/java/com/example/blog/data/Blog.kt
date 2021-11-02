package com.example.blog.data

data class Blog(
    val id: Int,
    var name: String,
    var introduction: String,
    var url: String,
    var numberOfNeighbor: Int,
    var backgroundImageUrl: String,
    var postList: ArrayList<Post>
) {
    constructor(id: Int) : this(
        id = id,
        name = "user${id}의 블로그",
        introduction = "",
        url = "user${id}.blockblog.com",
        numberOfNeighbor = 0,
        backgroundImageUrl = "",
        postList = arrayListOf()
    )
}
