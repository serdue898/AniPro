package com.example.anipro.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.anipro.data.database.entities.AnimeEntity

@Database(entities = [AnimeEntity::class], version = 1)
@TypeConverters(Converter::class)
abstract class AnimeDatabase : RoomDatabase() {
    abstract fun TwitDao(): AnimeDao
}