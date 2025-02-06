package com.app.githubu.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.githubu.data.repository.GeneralRepository
import com.app.githubu.data.repository.LocalRepository
import com.app.githubu.model.content.UserDetail
import com.app.githubu.model.content.UserRepo
import com.app.githubu.model.entities.LastViewUser
import com.app.githubu.utils.network.Result
import com.app.githubu.utils.orDash
import com.app.githubu.utils.orZero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val generalRepository: GeneralRepository,
    private val localRepository: LocalRepository
) : ViewModel() {

    private val _userDetail = MutableLiveData<Result<UserDetail>>()
    val userDetail: LiveData<Result<UserDetail>>
        get() = _userDetail

    fun requestDetailUser(username: String) {
        viewModelScope.launch {
            generalRepository.requestDetailUser(username)
                .collect {
                    _userDetail.value = it
                }
        }
    }

    private val _userDetailFromDb = MutableLiveData<UserDetail>()
    val userDetailFromDb: LiveData<UserDetail>
        get() = _userDetailFromDb

    fun requestDetailUserFromDb(username: String) {
        viewModelScope.launch {
            val lastViewUserSearch = localRepository.getDataByUsername(username)

            _userDetailFromDb.value = UserDetail(
                lastViewUserSearch.first().id.orZero(),
                lastViewUserSearch.first().username.orDash(),
                lastViewUserSearch.first().avatar.orDash(),
                lastViewUserSearch.first().name.orDash(),
                lastViewUserSearch.first().company.orDash(),
                lastViewUserSearch.first().blog.orDash(),
                lastViewUserSearch.first().location.orDash(),
                lastViewUserSearch.first().totalFollower.orZero(),
                lastViewUserSearch.first().totalFollowing.orZero(),
                lastViewUserSearch.first().totalRepo.orZero()
            )
        }

    }

    private val _username = MutableStateFlow("")

    val pagedUserRepos: Flow<PagingData<UserRepo>> = _username
        .filter { it.isNotEmpty() } // Prevent unnecessary requests
        .flatMapLatest { username ->
            generalRepository.requestPagingUserRepos(username)
        }
        .cachedIn(viewModelScope)

    fun setUsername(username: String) {
        _username.value = username
    }

    fun insertDataDetailToDb(userDetail: UserDetail){
        viewModelScope.launch {
            val lastViewUser = LastViewUser()
            lastViewUser.idUser = userDetail.id
            lastViewUser.username = userDetail.username
            lastViewUser.avatar = userDetail.avatar
            lastViewUser.name = userDetail.name
            lastViewUser.company = userDetail.company
            lastViewUser.blog = userDetail.blog
            lastViewUser.location = userDetail.location
            lastViewUser.totalFollower = userDetail.totalFollower
            lastViewUser.totalFollowing = userDetail.totalFollowing
            lastViewUser.totalRepo = userDetail.totalRepo

            localRepository.insertData(lastViewUser)
        }

    }
}