package com.example.twit.data.database

import com.example.twit.data.database.entities.TwitEntity
import com.example.twit.model.TwitData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TwitRepository @Inject constructor(private val twitDao: TwitDao) {
    val twits: Flow<List<TwitData>> = twitDao.getTwits().map { item->item.map { TwitData(it.id,it.description) } }


    suspend fun add(twitData: TwitData){
        twitDao.addTwit(TwitEntity(twitData.id,twitData.description))
    }
}