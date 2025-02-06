package com.app.githubu.model.resp


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RespDetailUser(
    @Json(name = "avatar_url")
    val avatarUrl: String?,
    @Json(name = "blog")
    val blog: String?,
    @Json(name = "company")
    val company: String?,
    @Json(name = "followers")
    val totalFollower: Int?,
    @Json(name = "following")
    val totalFollowing: Int?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "location")
    val location: String?,
    @Json(name = "login")
    val username: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "public_repos")
    val totalRepo: Int?

)