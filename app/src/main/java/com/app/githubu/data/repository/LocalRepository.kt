package com.app.githubu.data.repository

import com.app.githubu.database.dao.LastViewUserDao
import com.app.githubu.model.entities.LastViewUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val lastViewUserDao: LastViewUserDao
) {
    private var daoLastViewUser = lastViewUserDao

    suspend fun insertData(lastViewUser: LastViewUser) {
        CoroutineScope(Dispatchers.IO).launch {
            daoLastViewUser.insertLastViewUser(lastViewUser)
        }
    }

    suspend fun getAllLastViewUser(): List<LastViewUser> {
        return daoLastViewUser.loadAllLastViewUser().reversed().take(3)
    }

    suspend fun getDataByUsername(username: String): List<LastViewUser> {
        return daoLastViewUser.getDataByUsername(username)
    }
}