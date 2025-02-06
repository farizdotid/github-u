package com.app.githubu.model.resp


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

class RespUsers {
    @JsonClass(generateAdapter = true)
    data class RespUsersItem(
        @Json(name = "avatar_url")
        val avatarUrl: String?,
        @Json(name = "id")
        val id: Int?,
        @Json(name = "login")
        val login: String?
    )
}