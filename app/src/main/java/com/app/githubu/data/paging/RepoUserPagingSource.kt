package com.app.githubu.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.githubu.data.remote.GeneralDataSource
import com.app.githubu.model.content.UserRepo
import com.app.githubu.utils.orDash
import com.app.githubu.utils.orZero
import javax.inject.Inject

class RepoUserPagingSource @Inject constructor(
    private val generalDataSource: GeneralDataSource,
    private val username: String
) : PagingSource<Int, UserRepo>() {
    override fun getRefreshKey(state: PagingState<Int, UserRepo>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserRepo> {
        val position = params.key ?: 1
        return try {

            val result =
                generalDataSource.reqUserRepos(username = username, page = position, perPage = params.loadSize)

            val repoList = result.data?.map { data ->
                UserRepo(
                    id = data.id.orZero(),
                    name = data.name.orDash(),
                    description = data.description.orDash(),
                    repoUrl = data.htmlUrl.orEmpty(),
                )
            }.orEmpty()

            LoadResult.Page(
                data = repoList,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (repoList.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}