package com.example.twit.data.database

import com.example.twit.data.database.entities.AnimeEntity
import com.example.twit.model.TwitData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.sql.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimeRepository @Inject constructor(private val twitDao: AnimeDao) {
    val twits: Flow<List<TwitData>> = twitDao.getTwits().map { item->item.map { TwitData(it.id,"") } }


    suspend fun add(twitData: TwitData){
        val date = Date(System.currentTimeMillis())
        //twitDao.addTwit(AnimeEntity(twitData.id,twitData.description))
    }
}