package com.example.anipro.domain.network

import com.example.anipro.data.network.AnimeRepository
import com.example.anipro.model.AnimeItem
import javax.inject.Inject

class GetAnimeInfoUseCase @Inject constructor(private val repository: AnimeRepository) {
    suspend operator fun invoke(anime_id: Int): AnimeItem {
        return repository.getAnimeInfo(anime_id)

    }
}