package com.example.blog.data

data class Blog(
    var name: String,
    var url: String,
    var numberOfNeighbor: Int,
    var backgroundImageUrl: String,
    var profileImageUrl: String,
    var postList: ArrayList<Post>
)
