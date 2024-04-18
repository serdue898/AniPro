package com.example.twit.data.di

import com.example.twit.data.network.interceptor.ApiHeaderInterceptor
import com.example.twit.data.network.interceptor.ApiKeyInterceptor
import com.example.twit.data.network.response.AnimeClient
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)


class AnimeModule {
    @Singleton
    @Provides
    fun provideApiKeyInterceptor(): ApiKeyInterceptor =
        ApiKeyInterceptor()

    @Singleton
    @Provides
    fun provideApiHeaderInterceptor(): ApiHeaderInterceptor =
        ApiHeaderInterceptor()



    @Provides
    @Singleton
    fun provideHttpClient(
        apiKeyInterceptor: ApiKeyInterceptor,
        apiHeaderInterceptor: ApiHeaderInterceptor
    ) = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .addInterceptor(apiKeyInterceptor)
        .addInterceptor(apiHeaderInterceptor)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder().baseUrl("https://api.myanimelist.net/v2/")
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
            )
        ).client(okHttpClient).build()

    @Singleton
    @Provides
    fun provideAnimeClient(retrofit: Retrofit): AnimeClient {
        return retrofit.create(AnimeClient::class.java)
    }

}