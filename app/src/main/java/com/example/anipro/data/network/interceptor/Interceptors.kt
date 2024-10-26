package com.example.anipro.data.network.interceptor

import com.example.anipro.BuildConfig
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        // Api key query param from Constants (Uploaded to GIT repository)
        // val url = originalRequest.url.newBuilder()
        //    .addQueryParameter("api_key", Constants.API_KEY).build()

        // Api key query param from local.properties (Not uploaded to GIT repository) "588d4a2c79f71df470f2013ce46a46ef"
        val url = originalRequest.url.newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .build()

        val newRequest = originalRequest.newBuilder().url(url).build()

        return chain.proceed(newRequest)
    }
}

class ApiHeaderInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        var token: String?
        runBlocking {
            token = BuildConfig.API_KEY
        }
        val headers =
            originalRequest.headers.newBuilder().add("X-MAL-CLIENT-ID", "$token")
                .build()

        val newRequest = originalRequest.newBuilder().headers(headers).build()

        return chain.proceed(newRequest)
    }
}
