package com.app.githubu.utils.network.services

import com.app.githubu.model.resp.RespDetailUser
import com.app.githubu.model.resp.RespSearchUser
import com.app.githubu.model.resp.RespUserRepos
import com.app.githubu.model.resp.RespUsers
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GeneralServices {
    @GET("users")
    suspend fun requestUsers(
        @Query("page") page:Int? = null,
        @Query("per_page") perPage:Int? = null,
    ): Response<List<RespUsers.RespUsersItem>>

    @GET("search/users")
    suspend fun requestSearchUsers(
        @Query("q") username: String? = null
    ): Response<RespSearchUser>

    @GET("users/{username}")
    suspend fun requestDetailUser(
        @Path("username") username: String
    ): Response<RespDetailUser>

    @GET("users/{username}/repos")
    suspend fun requestUserRepos(
        @Path("username") username: String
    ): Response<List<RespUserRepos.RespUserRepoItem>>
}