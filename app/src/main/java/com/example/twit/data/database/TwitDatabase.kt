package com.example.twit.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.twit.data.database.entities.TwitEntity

@Database(entities = [TwitEntity::class], version = 1)
abstract class TwitDatabase: RoomDatabase() {
    abstract fun TwitDao(): TwitDao
}