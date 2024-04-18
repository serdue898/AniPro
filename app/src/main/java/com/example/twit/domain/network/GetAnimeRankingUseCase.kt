package com.example.twit.domain.network

import com.example.twit.data.network.AnimeRepository
import com.example.twit.model.AnimeItem
import javax.inject.Inject

class getAnimeRankingUseCase @Inject constructor(private val repository: AnimeRepository) {
    suspend operator fun invoke(): List<AnimeItem> {
        return repository.getAnimeRanking()

    }
}