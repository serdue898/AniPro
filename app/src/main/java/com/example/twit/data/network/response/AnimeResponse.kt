package com.example.twit.data.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AnimeResponse(
    @SerializedName("data")
    @Expose
    val data: List<nodeItem>,
    @SerializedName("paging")
    @Expose
    val paging: paging
)


data class paging(
    val previous: String,
    val next: String
)


data class nodeItem(
    @SerializedName("node")
    @Expose
    val node: node


)

data class node(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("title")
    @Expose
    val title: String?,

    @SerializedName("main_picture")
    @Expose
    val main_picture: main_picture?,
    @SerializedName("alternative_titles")
    @Expose
    val alternative_titles: alternative_titles?,
    @SerializedName("start_date")
    @Expose
    val start_date: String?,
    @SerializedName("end_date")
    @Expose
    val end_date: String?,
    @SerializedName("synopsis")
    @Expose
    val synopsis: String?,
    @SerializedName("mean")
    @Expose
    val mean: Float?,
    @SerializedName("rank")
    @Expose
    val rank: Int?,
    @SerializedName("popularity")
    @Expose
    val popularity: Int?,
    @SerializedName("num_list_users")
    @Expose
    val num_list_users: Int?,
    @SerializedName("num_scoring_users")
    @Expose
    val num_scoring_users: Int?,
    @SerializedName("nsfw")
    @Expose
    val nsfw: String?,
    @SerializedName("genres")
    @Expose
    val genres: Array<genre>?,
    @SerializedName("created_at")
    @Expose
    val created_at: String?,
    @SerializedName("updated_at")
    @Expose
    val updated_at: String?,
    @SerializedName("media_type")
    @Expose
    val media_type: String?,
    @SerializedName("status")
    @Expose
    val status: String?,
    @SerializedName("num_episodes")
    @Expose
    val num_episodes: Int?,
    @SerializedName("start_season")
    @Expose
    val start_season: start_season?,
    @SerializedName("broadcast")
    @Expose
    val broadcast: broadcast?,
    @SerializedName("source")
    @Expose
    val source: String?,
    @SerializedName("average_episode_duration")
    @Expose
    val average_episode_duration: Int?,
    @SerializedName("rating")
    @Expose
    val rating: String?,
    @SerializedName("studios")
    @Expose
    val studios: Array<studio>?
)

data class studio(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String)

data class broadcast(
    @SerializedName("day_of_the_week")
    @Expose
    val day_of_the_week
    : String,
    @SerializedName("start_time")
    @Expose
    val start_time
    : String?
)

data class start_season(
    @SerializedName("year")
    @Expose
    val year: Int,
    @SerializedName("season")
    @Expose
    val season: String)
data class genre(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String)
data class main_picture(
    @SerializedName("large")
    @Expose
    val large: String?,
    @SerializedName("medium")
    @Expose
    val medium: String)
data class alternative_titles(
    @SerializedName("synonyms")
    @Expose
    val synonyms: Array<String>?,
    @SerializedName("en")
    @Expose
    val en: String?,
    @SerializedName("ja")
    @Expose
    val ja: String?
)
