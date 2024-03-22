package com.example.twit.data.network

import com.example.twit.data.network.response.AnimeResponse
import retrofit2.http.GET

interface AnimeClient {
@GET("anime")
suspend fun getAnimes():AnimeResponse

}