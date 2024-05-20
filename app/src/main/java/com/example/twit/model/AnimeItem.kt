package com.example.twit.model



data class AnimeItem(
    val id: Int,
    val title: String,
    val main_picture: main_picture,
    val alternative_titles: alternative_titles,
    val start_date: String,
    val end_date: String,
    val synopsis: String,
    val mean: Float,
    val rank: Int,
    val popularity: Int,
    val num_list_users: Int,
    val num_scoring_users: Int,
    val nsfw: String,
    val genres: Array<genre>,
    val created_at: String,
    val updated_at: String,
    val media_type: String,
    val status: String,
    val num_episodes: Int,
    val start_season: start_season,
    val broadcast: broadcast,
    val source:String,
    val average_episode_duration:Int,
    val rating:String,
    val studios: Array<studio>

)
data class studio(val id:Int,val name:String)

data class broadcast(
    val day_of_the_week
    : String, val start_time
    : String
)

data class start_season(val year: Int, val season: String)
data class genre(val id: Int, val name: String)
data class main_picture(val large: String?, val medium: String)
data class alternative_titles(
    val synonyms: Array<String>,
    val en: String,
    val ja: String
)