package com.example.twit.domain.database

import com.example.twit.data.database.AnimeRepository
import com.example.twit.model.AnimeData
import com.example.twit.model.TwitData
import javax.inject.Inject

class AddAnimeUseCase @Inject constructor(private val repository: AnimeRepository){
    suspend operator fun invoke(animeData: AnimeData){
        repository.addAnimeL(animeData)
    }
}