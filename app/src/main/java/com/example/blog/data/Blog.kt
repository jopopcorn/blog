package com.example.blog.data

data class Blog(
    var id: Int, // 블로그 id는 유저 id와 동일
    var name: String,
    var nickname: String,
    var introduction: String,
    var url: String,
    var numberOfNeighbor: Int,
    var backgroundImageUrl: String
) {
    constructor(): this(
        id = 0,
        name = "",
        nickname = "",
        introduction = "",
        url = "",
        numberOfNeighbor = 0,
        backgroundImageUrl = ""
    )

    constructor(id: Int) : this(
        id = id,
        name = "user${id}의 블로그",
        nickname = "user${id}",
        introduction = "",
        url = "user${id}.blockblog.com",
        numberOfNeighbor = 0,
        backgroundImageUrl = ""
    )
}
