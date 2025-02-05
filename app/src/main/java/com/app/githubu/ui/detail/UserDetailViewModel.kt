package com.app.githubu.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.githubu.data.repository.GeneralRepository
import com.app.githubu.model.content.UserDetail
import com.app.githubu.utils.network.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val generalRepository: GeneralRepository,
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
}