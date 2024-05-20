package com.example.twit.data.network.response

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("anime/{anime_id}")
    suspend fun getAnimeInfo(
        @Path("anime_id") animeId: Int,
        @Query("fields") fields: String? = "main_picture,end_date,start_date,synopsis,mean,rank,popularity,num_list_users,num_scoring_users,nsfw,created_at,updated_at,media_type,status,genres,my_list_status,num_episodes,start_season,broadcast,source,average_episode_duration,rating,pictures,background,related_anime,related_manga,recommendations,studios,statistics",
    ): Response<Node>

}