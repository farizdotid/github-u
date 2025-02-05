package com.app.githubu.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.githubu.data.repository.GeneralRepository
import com.app.githubu.model.content.User
import com.app.githubu.utils.network.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val generalRepository: GeneralRepository,
) : ViewModel() {
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
}