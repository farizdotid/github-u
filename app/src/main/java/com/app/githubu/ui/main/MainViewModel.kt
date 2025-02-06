package com.app.githubu.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.githubu.data.repository.GeneralRepository
import com.app.githubu.data.repository.LocalRepository
import com.app.githubu.model.content.User
import com.app.githubu.model.content.UserDetail
import com.app.githubu.model.entities.LastViewUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.app.githubu.utils.network.Result

@HiltViewModel
class MainViewModel @Inject constructor(
    private val generalRepository: GeneralRepository,
    private val localRepository: LocalRepository
) : ViewModel() {

    val pagedUsers: Flow<PagingData<User>> = generalRepository.requestPagingUsers()
        .cachedIn(viewModelScope)

    private val _userList = MutableLiveData<Result<ArrayList<User>>>()
    val userList: LiveData<Result<ArrayList<User>>>
        get() = _userList

    fun requestUsers() {
        viewModelScope.launch {
            generalRepository.requestUsers()
                .collect {
                    _userList.value = it
                }
        }
    }
    fun requestSearchUsers(username: String) {
        viewModelScope.launch {
            generalRepository.requestSearchUsers(username)
                .collect {
                    _userList.value = it
                }
        }
    }

    suspend fun getAllDataViewedUsers() : List<LastViewUser> {
        return localRepository.getAllLastViewUser()
    }
}