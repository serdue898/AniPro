package com.example.twit.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.twit.data.database.entities.AnimeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {
    @Query("SELECT * from AnimeEntity")
    fun getTwits(): Flow<List<AnimeEntity>>
    @Insert
    suspend fun addTwit(item: AnimeEntity)
}