package com.example.anipro.domain.network

import com.example.anipro.data.network.AnimeRepository
import com.example.anipro.model.AnimeItem
import javax.inject.Inject

class SearchAnimeUseCase @Inject constructor(private val repository: AnimeRepository) {
    suspend operator fun invoke(query: String): List<AnimeItem> {
        return repository.getAnime(query)

    }
}