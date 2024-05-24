package com.example.twit.data.mappers

import com.example.twit.data.database.entities.AnimeEntity
import com.example.twit.data.network.response.AnimeResponse
import com.example.twit.data.network.response.Node
import com.example.twit.model.AnimeData
import com.example.twit.model.AnimeItem
import javax.inject.Inject


class LocalItem2ToItemMapper @Inject constructor() : TemplateMapper<AnimeEntity, AnimeData> {
    override fun map(input: AnimeEntity): AnimeData {
        return AnimeData(
            id = input.id,
            dateStart = input.dateStart,
            episodes = input.episodes,
            dateEnd = input.dateEnd
        )
    }
}
class ItemMapper2ToLocalItem@Inject constructor() : TemplateMapper<AnimeData, AnimeEntity> {
    override fun map(input: AnimeData): AnimeEntity {
        return AnimeEntity(
            id = input.id,
            dateStart = input.dateStart,
            episodes = input.episodes,
            dateEnd = input.dateEnd
        )
    }
}