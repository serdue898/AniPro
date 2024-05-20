package com.example.twit.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TwitEntity(
    @PrimaryKey()
    val id:Int,
    val description: String
)
