package com.app.githubu.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.githubu.database.dao.LastViewUserDao
import com.app.githubu.model.entities.LastViewUser

@Database(entities = [LastViewUser::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    abstract fun getLastViewUserDao(): LastViewUserDao?
}