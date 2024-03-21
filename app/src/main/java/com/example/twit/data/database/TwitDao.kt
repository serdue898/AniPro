package com.example.twit.data.database

import androidx.room.*
import com.example.twit.data.database.entities.TwitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TwitDao {
    @Query("SELECT * from TwitEntity")
    fun getTwits(): Flow<List<TwitEntity>>
    @Insert
    suspend fun addTwit(item: TwitEntity)
}