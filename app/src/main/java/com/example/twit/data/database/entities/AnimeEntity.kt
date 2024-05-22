package com.example.twit.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class AnimeEntity(
    @PrimaryKey
    val id:Int,
    val dateEnd: Date = Date(System.currentTimeMillis()),
    val dateStart: String = "",
    val episodes: Int = 0,
)
