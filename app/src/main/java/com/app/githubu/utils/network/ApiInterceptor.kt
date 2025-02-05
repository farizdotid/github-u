package com.app.githubu.utils.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .build()

        val requestBuilder =  original.newBuilder()
            .addHeader("Accept", "application/vnd.github+json")
            .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}