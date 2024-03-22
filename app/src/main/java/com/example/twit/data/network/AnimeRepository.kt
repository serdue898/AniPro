package com.example.twit.data.network

import com.example.twit.data.network.response.AnimeResponse
import javax.inject.Inject

class AnimeRepository @Inject constructor(private val api:AnimeService) {
    suspend fun getAnime():AnimeResponse{
        return api.getAnime()
    }

}