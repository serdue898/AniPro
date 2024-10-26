package com.example.anipro.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.anipro.data.database.entities.AnimeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {
    @Query("SELECT * from AnimeEntity")
    fun getTwits(): Flow<List<AnimeEntity>>

    @Insert
    suspend fun addAnime(item: AnimeEntity)

    @Update
    suspend fun updateAnime(item: AnimeEntity)

    @Query("SELECT * from AnimeEntity Where id = :id")
    fun getAnimebyId(id:Int): Flow<List<AnimeEntity>>
}