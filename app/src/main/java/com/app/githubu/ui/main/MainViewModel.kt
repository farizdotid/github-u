package com.app.githubu.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.githubu.data.repository.GeneralRepository
import com.app.githubu.model.content.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val generalRepository: GeneralRepository,
) : ViewModel() {

    val pagedUsers: Flow<PagingData<User>> = generalRepository.requestUsers()
        .cachedIn(viewModelScope)

//    fun requestSearchUsers(username: String) {
//        viewModelScope.launch {
//            generalRepository.requestSearchUsers(username)
//                .collect {
//                    _userList.value = it
//                }
//        }
//    }
}