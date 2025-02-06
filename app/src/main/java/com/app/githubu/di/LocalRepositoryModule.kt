package com.app.githubu.di

import com.app.githubu.data.repository.LocalRepository
import com.app.githubu.database.MyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object LocalRepositoryModule {
    @Provides
    @Singleton
    fun providesLocalRepository(myDatabase: MyDatabase): LocalRepository {
        return LocalRepository(myDatabase.getLastViewUserDao())
    }
}