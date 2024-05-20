package com.example.twit.domain.network

import com.example.twit.data.network.AnimeRepository
import com.example.twit.model.AnimeItem
import javax.inject.Inject

class getAnimeInfoUseCase @Inject constructor(private val repository: AnimeRepository) {
    suspend operator fun invoke(anime_id:Int): AnimeItem {
        return repository.getAnimeInfo(anime_id)

    }
}