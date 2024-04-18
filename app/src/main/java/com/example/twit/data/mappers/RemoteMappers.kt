package com.example.twit.data.mappers

import com.example.twit.data.network.response.node
import com.example.twit.data.network.utils.Node
import com.example.twit.model.AnimeItem
import javax.inject.Inject

class RemoteItem2ToItemMapper @Inject constructor() : TemplateMapper<Node, AnimeItem> {
    override fun map(input: node): AnimeItem {
        return AnimeItem(
            id = input.id,
            title = input.title,
            main_picture = input.main_picture,
            genres = input.genres,
            studios = input.studios,
            source = input.source,
            status = input.status,
            broadcast = input.broadcast,
            rating = input.rating,
            alternative_titles = input.alternative_titles,
            average_episode_duration = input.average_episode_duration,
            created_at = input.created_at,
            end_date = input.end_date,
            mean = input.mean,
            media_type = input.media_type,
            nsfw = input.nsfw,
            num_episodes = input.num_episodes,
            num_list_users = input.num_list_users,
            num_scoring_users = input.num_scoring_users,
            popularity = input.popularity,

            rank = input.rank,
            start_date = input.start_date,
            start_season = input.start_season,
            synopsis = input.synopsis,
            updated_at = input.updated_at



        )
    }
}



private fun getItemId(itemId: Int?): Int {
    return itemId?.takeIf { true } ?: run {
        throw IllegalArgumentException("itemId is required for screen")
    }
}