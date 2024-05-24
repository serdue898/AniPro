package com.example.anipro.domain.network

import com.example.anipro.data.network.AnimeRepository
import com.example.anipro.model.AnimeItem
import javax.inject.Inject

class getAnimeRankingUseCase @Inject constructor(private val repository: AnimeRepository) {
    suspend operator fun invoke(): List<AnimeItem> {
        return repository.getAnimeRanking()

    }
}