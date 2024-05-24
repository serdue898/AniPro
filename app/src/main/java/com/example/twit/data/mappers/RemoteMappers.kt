package com.example.twit.data.mappers

import com.example.twit.data.network.response.Alternative_titles
import com.example.twit.data.network.response.AnimeResponse
import com.example.twit.data.network.response.Broadcast
import com.example.twit.data.network.response.Genre
import com.example.twit.data.network.response.Main_picture
import com.example.twit.data.network.response.Node
import com.example.twit.data.network.response.Start_season
import com.example.twit.data.network.response.Studio
import com.example.twit.model.AnimeItem
import com.example.twit.model.alternative_titles
import com.example.twit.model.broadcast
import com.example.twit.model.genre
import com.example.twit.model.main_picture
import com.example.twit.model.start_season
import com.example.twit.model.studio
import javax.inject.Inject

class RemoteItem2ToItemMapper @Inject constructor() : TemplateMapper<Node, AnimeItem> {
    override fun map(input: Node): AnimeItem {
        return AnimeItem(
            id = input.id,
            title = input.title ?: "",
            main_picture = mapMainPicture(input.main_picture),
            genres = mapGenres(input.genres),
            studios = mapStudio(input.studios),
            source = input.source?: "no asignado",
            status = input.status?: "no fijado",
            broadcast = mapBroadcast(input.broadcast),
            rating = input.rating?: "sin valoraciones",
            alternative_titles = mapAlternativeTitles(input.alternative_titles),
            average_episode_duration = input.average_episode_duration?: 0,
            created_at = input.created_at?: "no asignado",
            end_date = input.end_date?: "no asignado ",
            mean = input.mean?: 0f,
            media_type = input.media_type?: "",
            nsfw = input.nsfw?: "no valorado",
            num_episodes = input.num_episodes?: 0,
            num_list_users = input.num_list_users?:0,
            num_scoring_users = input.num_scoring_users?: 0,
            popularity = input.popularity?: 0,
            rank = input.rank?: 0,
            start_date = input.start_date?: "sin fecha inicio",
            start_season = mapStartSeason(input.start_season),
            synopsis = input.synopsis?: "sin sinopsis",
            updated_at = input.updated_at?: "no updated",
        )
    }
}

fun mapMainPicture(input:Main_picture?):main_picture{
    return if (input != null) {
        main_picture(
            large = input.large?:"",
            medium = input.medium
        )
    }else{
        main_picture(
            large = "",
            medium = ""
        )
    }
}

fun mapGenres(input:Array<Genre>?):Array<genre>{
    return input?.map {
        genre(
            id = it.id,
            name = it.name
        )
    }?.toTypedArray()?: emptyArray()
}


fun mapStudio(input:Array<Studio>?):Array<studio>{
    return input?.map {
        studio(
            id = it.id,
            name = it.name
        )
    }?.toTypedArray()?: emptyArray()
}

fun mapBroadcast(input:Broadcast?): broadcast {
    return if (input != null) {
        broadcast(
            day_of_the_week = input.day_of_the_week,
            start_time = input.start_time ?:"no asignado"
        )
    }else{
        broadcast(
            day_of_the_week = "no asignado",
            start_time = "no asignado"
        )
    }
}

fun mapAlternativeTitles(input:Alternative_titles?): alternative_titles {
    return if (input != null) {
        alternative_titles(
            synonyms = input.synonyms?: emptyArray(),
            en = input.en?:"",
            ja = input.ja?:""
        )
    }else{
        alternative_titles(
            synonyms = emptyArray(),
            en = "",
            ja = ""
        )
    }
}

fun mapStartSeason(input: Start_season?): start_season {
    return if (input != null) {
        start_season(
            year = input.year,
            season = input.season
        )
    }else{
        start_season(
            year = 0,
            season = ""
        )
    }
}

private fun getItemId(itemId: Int?): Int {
    return itemId?.takeIf { true } ?: run {
        throw IllegalArgumentException("itemId is required for screen")
    }
}