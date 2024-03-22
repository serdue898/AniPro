package com.example.twit.data.network

import com.example.twit.data.network.response.AnimeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AnimeService @Inject constructor(private val animeClient: AnimeClient) {
    suspend fun getAnime():AnimeResponse{
        return withContext(Dispatchers.IO){
            animeClient.getAnimes()
        }

    }
}