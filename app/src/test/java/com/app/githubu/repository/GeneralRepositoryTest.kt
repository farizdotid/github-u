package com.app.githubu.repository

import app.cash.turbine.test
import com.app.githubu.data.remote.GeneralDataSource
import com.app.githubu.data.repository.GeneralRepository
import com.app.githubu.model.content.User
import com.app.githubu.model.content.UserDetail
import com.app.githubu.model.resp.RespDetailUser
import com.app.githubu.model.resp.RespUsers
import com.app.githubu.utils.MainDispatcherRule
import com.app.githubu.utils.network.ErrorApi
import com.app.githubu.utils.network.Result
import com.app.githubu.utils.orDash
import com.app.githubu.utils.orZero
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class GeneralRepositoryTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var generalDataSource: GeneralDataSource

    private lateinit var repository: GeneralRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = GeneralRepository(generalDataSource)
    }

    @Test
    fun `requestUsers should emit error when data is unavailable`() = runTest {
        val mockResult = Result.error<List<RespUsers.RespUsersItem>>(
            message = "Failed to get data",
            error = ErrorApi("Error")
        )

        Mockito.`when`(generalDataSource.reqUsers()).thenReturn(mockResult)

        repository.requestUsers().test {
            // Then
            assertEquals(Result.loading<List<RespUsers.RespUsersItem>>(), awaitItem())
            val result = awaitItem()
            assertEquals(Result.Status.ERROR, result.status)
            assertEquals("Failed to get data", result.message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `requestDetailUser should emit success with correct details`() = runTest {
        // Given
        val mockRespDetailUser = RespDetailUser(
            id = 1,
            username = "octocat",  // Assuming 'login' maps to 'username'
            avatarUrl = "url",
            name = "Octocat",
            company = "GitHub",
            blog = "https://github.com",
            location = "San Francisco",
            totalFollower = 100,  // Assuming 'followers' maps to 'totalFollower'
            totalFollowing = 50,  // Assuming 'following' maps to 'totalFollowing'
            totalRepo = 10  // Assuming 'publicRepos' maps to 'totalRepo'
        )

        val mockDetailResult = Result.success(mockRespDetailUser)

        // Mocking the DataSource method
        Mockito.`when`(generalDataSource.reqDetailUser("octocat")).thenReturn(mockDetailResult)

        // When
        repository.requestDetailUser("octocat").test {
            // Then
            assertEquals(Result.loading<UserDetail>(), awaitItem())  // Specify the correct type
            val result = awaitItem()

            // Map RespDetailUser to UserDetail
            val expectedUserDetail = UserDetail(
                id = mockRespDetailUser.id.orZero(),
                username = mockRespDetailUser.username.orDash(),
                avatar = mockRespDetailUser.avatarUrl.orDash(),
                name = mockRespDetailUser.name.orDash(),
                company = mockRespDetailUser.company.orDash(),
                blog = mockRespDetailUser.blog.orDash(),
                location = mockRespDetailUser.location.orDash(),
                totalFollower = mockRespDetailUser.totalFollower.orZero(),
                totalFollowing = mockRespDetailUser.totalFollowing.orZero(),
                totalRepo = mockRespDetailUser.totalRepo.orZero()
            )

            assertEquals(Result.Status.SUCCESS, result.status)  // Check if the status is SUCCESS
            assertEquals(expectedUserDetail, result.data)  // Compare with UserDetail
            cancelAndIgnoreRemainingEvents()
        }
    }

}