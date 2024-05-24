package com.example.anipro.data.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AnimeResponse(
    @SerializedName("data")
    @Expose
    val data: List<NodeItem>,
    @SerializedName("paging")
    @Expose
    val paging: Paging
)


data class Paging(
    val previous: String,
    val next: String
)


data class NodeItem(
    @SerializedName("node")
    @Expose
    val node: Node


)

data class Node(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("title")
    @Expose
    val title: String?,

    @SerializedName("main_picture")
    @Expose
    val main_picture: Main_picture?,
    @SerializedName("alternative_titles")
    @Expose
    val alternative_titles: Alternative_titles?,
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
    val genres: Array<Genre>?,
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
    val start_season: Start_season?,
    @SerializedName("broadcast")
    @Expose
    val broadcast: Broadcast?,
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
    val studios: Array<Studio>?
)

data class Studio(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String
)

data class Broadcast(
    @SerializedName("day_of_the_week")
    @Expose
    val day_of_the_week
    : String,
    @SerializedName("start_time")
    @Expose
    val start_time
    : String?
)

data class Start_season(
    @SerializedName("year")
    @Expose
    val year: Int,
    @SerializedName("season")
    @Expose
    val season: String
)

data class Genre(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String
)

data class Main_picture(
    @SerializedName("large")
    @Expose
    val large: String?,
    @SerializedName("medium")
    @Expose
    val medium: String
)

data class Alternative_titles(
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
