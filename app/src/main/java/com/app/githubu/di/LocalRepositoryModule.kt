package com.app.githubu.di

import com.app.githubu.data.repository.LocalRepository
import com.app.githubu.database.MyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@InstallIn(SingletonComponent::class)
@Module
class LocalRepositoryModule {
    @Singleton
    @Provides
    fun providesLocalRepository(myDatabase: MyDatabase): LocalRepository? {
        return myDatabase.getLastViewUserDao()?.let { LocalRepository(it) }
    }
}