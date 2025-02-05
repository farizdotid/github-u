package com.app.githubu.di

import com.app.githubu.utils.network.services.GeneralServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiServiceModule {
    @Provides
    @Singleton
    fun provideGeneralService(retrofit: Retrofit): GeneralServices {
        return retrofit.create(GeneralServices::class.java)
    }
}