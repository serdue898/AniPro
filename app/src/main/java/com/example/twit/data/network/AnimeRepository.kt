package com.example.twit.data.network

import com.example.twit.data.mappers.TemplateMapper
import com.example.twit.data.network.response.AnimeClient
import com.example.twit.data.network.response.AnimeResponse

import com.example.twit.data.network.utils.handleNetworkResult
import com.example.twit.data.network.utils.handleRequest
import com.example.twit.model.AnimeItem
import javax.inject.Inject

class AnimeRepository @Inject constructor(
    private val remoteItemsApi: AnimeClient,
    private val remoteItem2ItemMapper: TemplateMapper<AnimeResponse, AnimeItem>
) {
    suspend fun getAnime(query: String):List<AnimeItem>{
        var result: List<AnimeItem>? = null
        val resultApi = remoteItemsApi.getAnimes(query)
        val data = resultApi.handleRequest { data ->
            data.data.map { remoteItem2ItemMapper.map(it.node) }
        }
        data.handleNetworkResult { data -> result = data }
        return result!!
    }

    suspend fun getAnimeRanking():List<AnimeItem>{
        var result: List<AnimeItem>? = null
        val resultApi = remoteItemsApi.getAnimesRanking()
        val data = resultApi.handleRequest { data ->
            data.data.map { remoteItem2ItemMapper.map(it.node) }
        }
        data.handleNetworkResult { data -> result = data }
        return result!!
    }
}