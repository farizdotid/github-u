package com.app.githubu.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.githubu.model.entities.LastViewUser

@Dao
interface LastViewUserDao {
    @Query("SELECT * FROM tbl_last_view_user GROUP BY username")
    suspend fun loadAllLastViewUser(): List<LastViewUser>

    @Insert
    suspend fun insertLastViewUser(lastViewUser: LastViewUser)

    @Query("SELECT * FROM tbl_last_view_user WHERE username=:username")
    suspend fun getDataByUsername(username: String): List<LastViewUser>


}