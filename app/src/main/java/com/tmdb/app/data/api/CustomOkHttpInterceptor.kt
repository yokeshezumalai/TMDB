package com.tmdb.app.data.api

import com.tmdb.app.AppConfig
import okhttp3.Interceptor
import okhttp3.Response

class CustomOkHttpInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        // Request customization: add request headers
        val requestBuilder = original.newBuilder()
            .header("Accept", "application/json")
            requestBuilder.method(original.method(), original.body())

        val request = requestBuilder.build()
        return chain.proceed(request)

    }
}