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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import timber.log.Timber
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