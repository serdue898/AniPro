package com.example.twit.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.twit.data.database.entities.AnimeEntity

@Database(entities = [AnimeEntity::class], version = 1)
abstract class AnimeDatabase: RoomDatabase() {
    abstract fun TwitDao(): AnimeDao
}