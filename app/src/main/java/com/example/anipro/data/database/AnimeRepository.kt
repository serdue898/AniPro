package com.example.anipro.data.database

import com.example.anipro.data.mappers.ItemMapper2ToLocalItem
import com.example.anipro.data.mappers.LocalItem2ToItemMapper
import com.example.anipro.model.AnimeData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimeRepository @Inject constructor(
    private val animeDao: AnimeDao,
    private val localItem2ItemMapper: LocalItem2ToItemMapper,
    private val itemMapper2ToLocalItem: ItemMapper2ToLocalItem
) {
    val animes: Flow<List<AnimeData>> =
        animeDao.getTwits().map { item -> item.map { localItem2ItemMapper.map(it) } }

    suspend fun addAnimeL(anime: AnimeData) {
        animeDao.addAnime(itemMapper2ToLocalItem.map(anime))
    }
}