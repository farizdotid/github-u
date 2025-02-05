package com.app.githubu.utils.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorApi(
    @Json(name = "message")
    val message: String? = "",
    @Json(name = "error_code")
    val errorCode: Int? = 0,
    @Json(name = "success")
    val success: Boolean? = false
)