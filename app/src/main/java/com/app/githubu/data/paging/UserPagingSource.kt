package com.app.githubu.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.githubu.data.remote.GeneralDataSource
import com.app.githubu.model.content.User
import com.app.githubu.utils.orZero
import javax.inject.Inject

class UserPagingSource @Inject constructor(
    private val generalDataSource: GeneralDataSource
) : PagingSource<Int, User>() {
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val position = params.key ?: 1
        return try {

            val result = generalDataSource.reqUsers(page = position, perPage = params.loadSize)

            val userList = result.data?.map { data ->
                User(
                    id = data.id.orZero(),
                    username = data.login.orEmpty(),
                    avatarUrl = data.avatarUrl.orEmpty()
                )
            }.orEmpty()

            LoadResult.Page(
                data = userList,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (userList.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}