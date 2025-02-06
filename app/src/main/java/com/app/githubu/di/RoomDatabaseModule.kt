package com.app.githubu.di

import android.app.Application
import androidx.room.Room
import com.app.githubu.database.MyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@InstallIn(SingletonComponent::class)
@Module
class RoomDatabaseModule {
    private lateinit var myDatabase: MyDatabase

    @Singleton
    @Provides
    fun providesRoomDatabase(application: Application): MyDatabase {
        myDatabase = Room.databaseBuilder(application, MyDatabase::class.java, "githubu_db")
                .fallbackToDestructiveMigration()
                .build()
        return myDatabase
    }

    @Singleton
    @Provides
    fun providesLastViewUserDAO(myDatabase: MyDatabase) = myDatabase.getLastViewUserDao()
}