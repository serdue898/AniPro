package com.example.twit.model

import android.graphics.drawable.PaintDrawable
import androidx.room.PrimaryKey
import java.time.LocalDateTime

data class TwitData(
    val id:Int = LocalDateTime.now().hashCode(),
    val description: String
)