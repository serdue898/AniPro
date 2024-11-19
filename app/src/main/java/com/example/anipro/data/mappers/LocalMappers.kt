package com.example.anipro.data.mappers

import com.example.anipro.data.database.entities.AnimeEntity
import com.example.anipro.model.AnimeData
import javax.inject.Inject


class LocalItem2ToItemMapper @Inject constructor() : TemplateMapper<AnimeEntity, AnimeData> {
    override fun map(input: AnimeEntity): AnimeData {
        return AnimeData(
            id = input.id,
            dateStart = input.dateStart,
            dateEnd = input.dateEnd,
            title = input.title,
            image = input.image,
            isNotification = input.isNotification
        )
    }
}

class ItemMapper2ToLocalItem @Inject constructor() : TemplateMapper<AnimeData, AnimeEntity> {
    override fun map(input: AnimeData): AnimeEntity {
        return AnimeEntity(
            id = input.id,
            dateStart = input.dateStart,
            dateEnd = input.dateEnd,
            title = input.title,
            image = input.image,
            isNotification = input.isNotification
        )
    }
}