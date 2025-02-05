package com.app.githubu.di

import android.content.Context
import com.app.githubu.BuildConfig
import com.app.githubu.utils.network.callFactoryExt
import com.app.githubu.utils.network.converter.SingleToArrayAdapter
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.BINARY)
@Qualifier
private annotation class InternalApi

@InstallIn(SingletonComponent::class)
@Module(includes = [ApiServiceModule::class, InterceptorModule::class])
object NetworkModule {
    private const val API_BASE_URL = BuildConfig.BASE_URL

    @Provides
    @Named(API_BASE_URL)
    fun provideBaseUrl(): String {
        return API_BASE_URL
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }
    }

    @Provides
    @Singleton
    fun provideChunkInterceptor(context: Context): ChuckerInterceptor {
        return ChuckerInterceptor(context)
    }

    // Use newBuilder() to customize so that thread-pool and connection-pool same are used
    @Provides
    fun provideOkHttpClientBuilder(
        @InternalApi okHttpClient: Lazy<OkHttpClient>
    ): OkHttpClient.Builder {
        return okHttpClient.get().newBuilder()
    }

    @InternalApi
    @Provides
    @Singleton
    fun provideBaseOkHttpClient(
        interceptor: DaggerSet<Interceptor>,
        cache: Cache
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(60, TimeUnit.SECONDS)
        builder.readTimeout(60, TimeUnit.SECONDS)
        builder.writeTimeout(60, TimeUnit.SECONDS)
        builder.interceptors().addAll(interceptor)
        builder.cache(cache)
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideCache(context: Context): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val cacheDir = context.cacheDir
        return Cache(cacheDir, cacheSize.toLong())
    }

    @Singleton
    @Provides
    fun provideMoshiAdapter(): Moshi = Moshi.Builder()
        .add(SingleToArrayAdapter.INSTANCE)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        moshi: Moshi,
        @InternalApi
        okHttpClient: Lazy<OkHttpClient>,
        @Named(API_BASE_URL) baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .callFactoryExt { okHttpClient.get().newCall(it) }
            .build()
    }
}