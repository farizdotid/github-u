package com.app.githubu.utils.network.services

import com.app.githubu.model.resp.RespUsers
import retrofit2.Response
import retrofit2.http.GET

interface GeneralServices {
    @GET("users")
    suspend fun requestUsers(): Response<List<RespUsers.RespUsersItem>>
}