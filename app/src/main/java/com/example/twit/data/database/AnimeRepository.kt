package com.example.twit.data.database

import com.example.twit.data.database.entities.AnimeEntity
import com.example.twit.data.mappers.ItemMapper2ToLocalItem
import com.example.twit.data.mappers.LocalItem2ToItemMapper
import com.example.twit.data.mappers.TemplateMapper
import com.example.twit.data.network.response.Node
import com.example.twit.model.AnimeData
import com.example.twit.model.AnimeItem
import com.example.twit.model.TwitData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.sql.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimeRepository @Inject constructor(
    private val animeDao: AnimeDao,
    private val localItem2ItemMapper: LocalItem2ToItemMapper,
    private val itemMapper2ToLocalItem: ItemMapper2ToLocalItem
) {
    val animes: Flow<List<AnimeData>> = animeDao.getTwits().map { item -> item.map { localItem2ItemMapper.map(it) }}
    suspend fun addAnimeL(anime: AnimeData) {
        animeDao.addAnime(itemMapper2ToLocalItem.map(anime))
    }
}