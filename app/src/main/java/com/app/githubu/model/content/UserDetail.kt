package com.app.githubu.model.content

data class UserDetail(
    val id: Int,
    val username: String,
    val avatar: String,
    val name: String,
    val company: String,
    val blog: String,
    val location: String,
    val totalFollower: Int,
    val totalFollowing: Int,
    val totalRepo: Int,
)
