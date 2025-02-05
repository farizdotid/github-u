package com.app.githubu.data.remote

import com.app.githubu.model.resp.RespSearchUser
import com.app.githubu.model.resp.RespUsers
import com.app.githubu.utils.network.ErrorApi
import com.app.githubu.utils.network.ErrorUtils
import com.app.githubu.utils.network.Result
import com.app.githubu.utils.network.services.GeneralServices
import retrofit2.Response
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject

class GeneralDataSource @Inject constructor(
    private val retrofit: Retrofit
) {
    private var generalServices: GeneralServices = retrofit.create(GeneralServices::class.java)

    suspend fun reqUsers(): Result<List<RespUsers.RespUsersItem>> {
        return getResponse(
            request = {
                generalServices.requestUsers()
            }
        )
    }

    suspend fun reqSearchUsers(username:String): Result<RespSearchUser> {
        return getResponse(
            request = {
                generalServices.requestSearchUsers(username)
            }
        )
    }

    private suspend fun <T> getResponse(
        request: suspend () -> Response<T>
    ): Result<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                return Result.success(result.body())
            } else {
                val errorResponse = ErrorUtils.parseError(result, retrofit)
                Result.error(
                    "unknown error",
                    errorResponse
                )
            }
        } catch (e: Throwable) {
            Timber.d("debug -- GeneralDataSource.kt - ${e.message}")
            Result.error("Error ${e.message}", ErrorApi("Failed to get data"))
        }
    }
}