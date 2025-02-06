package com.app.githubu.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.githubu.model.entities.LastViewUser

@Dao
interface LastViewUserDao {
    @Query("SELECT * FROM tbl_last_view_user")
    fun loadAllLastViewUser(): LiveData<List<LastViewUser>>

    @Insert
    fun insertLastViewUser(lastViewUser: LastViewUser)
}