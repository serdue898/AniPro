package com.example.anipro.data.network

import com.example.anipro.data.mappers.TemplateMapper
import com.example.anipro.data.network.response.AnimeClient
import com.example.anipro.data.network.response.Node

import com.example.anipro.data.network.utils.handleNetworkResult
import com.example.anipro.data.network.utils.handleRequest
import com.example.anipro.model.AnimeItem
import javax.inject.Inject

class AnimeRepository @Inject constructor(
    private val remoteItemsApi: AnimeClient,
    private val remoteItem2ItemMapper: TemplateMapper<Node, AnimeItem>
) {
    suspend fun getAnime(query: String): List<AnimeItem> {
        var result: List<AnimeItem>? = null
        val resultApi = remoteItemsApi.getAnimes(query)
        val data = resultApi.handleRequest { data ->
            data.data.map { remoteItem2ItemMapper.map(it.node) }
        }
        data.handleNetworkResult { data -> result = data }
        return result!!
    }

    suspend fun getAnimeRanking(): List<AnimeItem> {
        var result: List<AnimeItem>? = null
        val resultApi = remoteItemsApi.getAnimesRanking()
        val data = resultApi.handleRequest { data ->
            data.data.map { remoteItem2ItemMapper.map(it.node) }
        }
        data.handleNetworkResult { data -> result = data }
        return result!!
    }

    suspend fun getAnimeInfo(animeId: Int): AnimeItem {
        var result: AnimeItem? = null
        val resultApi = remoteItemsApi.getAnimeInfo(animeId)
        val data = resultApi.handleRequest { data ->
            remoteItem2ItemMapper.map(data)
        }
        data.handleNetworkResult { data -> result = data }
        return result!!
    }
}