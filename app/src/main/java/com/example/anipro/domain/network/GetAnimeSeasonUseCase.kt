package com.example.anipro.domain.network

import com.example.anipro.data.network.AnimeRepository
import com.example.anipro.model.AnimeItem
import javax.inject.Inject

class GetAnimeSeason @Inject constructor(private val repository: AnimeRepository) {
    suspend operator fun invoke( year:Int,season:String): List<AnimeItem> {
        return repository.getAnimeSeason(year,season)

    }
}