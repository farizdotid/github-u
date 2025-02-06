package com.app.githubu.data.repository

import androidx.lifecycle.LiveData
import com.app.githubu.database.dao.LastViewUserDao
import com.app.githubu.model.entities.LastViewUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocalRepository @Inject constructor(lastViewUserDao: LastViewUserDao) {
    private var daoLastViewUser = lastViewUserDao

    fun insertData(lastViewUser: LastViewUser) {
        CoroutineScope(Dispatchers.IO).launch {
            daoLastViewUser.insertLastViewUser(lastViewUser)
        }
    }

    fun getLastViewUser(): LiveData<List<LastViewUser>> {
        return daoLastViewUser.loadAllLastViewUser()
    }
}