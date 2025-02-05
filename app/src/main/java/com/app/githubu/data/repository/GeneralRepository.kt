package com.app.githubu.data.repository

import com.app.githubu.data.remote.GeneralDataSource
import com.app.githubu.model.content.User
import com.app.githubu.utils.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GeneralRepository @Inject constructor(
    private val generalDataSource: GeneralDataSource
) {
    suspend fun requestUsers(): Flow<Result<ArrayList<User>>> {
        return flow {
            emit(Result.loading())
            val result = generalDataSource.reqUsers()

            if (result.data?.isNotEmpty() == true) {
                val userList = arrayListOf<User>()

                result.data.forEachIndexed { index, data ->
                    val login = data.login.orEmpty()
                    val avatar = data.avatarUrl.orEmpty()

                    userList.add(User(login, avatar))
                }

                emit(Result.success(userList))
            } else {
                emit(Result.error("Failed to get data", result.error))
            }

        }.flowOn(Dispatchers.IO)
    }

    suspend fun requestSearchUsers(username:String): Flow<Result<ArrayList<User>>> {
        return flow {
            emit(Result.loading())
            val result = generalDataSource.reqSearchUsers(username)

            if (result.data?.totalCount != 0) {
                val userList = arrayListOf<User>()

                result.data?.items?.forEachIndexed { index, data ->
                    val login = data?.login.orEmpty()
                    val avatar = data?.avatarUrl.orEmpty()

                    userList.add(User(login, avatar))
                }

                emit(Result.success(userList))
            } else {
                emit(Result.error("Failed to get data", result.error))
            }

        }.flowOn(Dispatchers.IO)
    }
}