package com.app.githubu.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.app.githubu.database.MyDatabase
import com.app.githubu.database.dao.LastViewUserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@InstallIn(SingletonComponent::class)
@Module
class RoomDatabaseModule {
    private lateinit var myDatabase: MyDatabase

    @Singleton
    @Provides
    fun providesRoomDatabase(@ApplicationContext context: Context): MyDatabase {
        myDatabase = Room.databaseBuilder(context, MyDatabase::class.java, "githubu_db")
                .fallbackToDestructiveMigration()
                .build()
        return myDatabase
    }

    @Singleton
    @Provides
    fun providesLastViewUserDAO(myDatabase: MyDatabase): LastViewUserDao {
        return myDatabase.getLastViewUserDao()
    }
}