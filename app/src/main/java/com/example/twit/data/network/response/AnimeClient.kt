package com.example.twit.data.network.response

import com.example.twit.data.network.response.AnimeResponse
import com.example.twit.data.network.utils.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeClient {
@GET("anime")
suspend fun getAnimes(
    @Query("q") ignore: String? = "mob",
): Response<AnimeResponse>

    @GET("anime/ranking")
    suspend fun getAnimesRanking(
        @Query("ranking_type") ignore: String? = "all",
    ): Response<AnimeResponse>

}