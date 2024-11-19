package com.example.anipro.ui.screens.modifyAnime


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anipro.domain.database.AddAnimeUseCase
import com.example.anipro.domain.database.GetAnimeByIdUseCase
import com.example.anipro.domain.network.GetAnimeInfoUseCase
import com.example.anipro.model.AnimeData
import com.example.anipro.utils.NotificationHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import retrofit2.HttpException
import java.io.IOException
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ModifyViewModel @Inject constructor(
    private val getAnimeInfoUseCase: GetAnimeInfoUseCase,
    private val addAnimeUseCase: AddAnimeUseCase,
    private val getAnimeByIdUseCase: GetAnimeByIdUseCase


) : ViewModel() {
    private val _uiState = MutableStateFlow<ModifyStateUI>(ModifyStateUI.Loading)
    val uiState: StateFlow<ModifyStateUI> = _uiState.asStateFlow()

    fun getAnimeInfo(id: Int) {
        viewModelScope.launch {
            _uiState.update { ModifyStateUI.Loading }
            _uiState.update {
                try {
                    val animeDataList = getAnimeByIdUseCase(id).firstOrNull()
                    val isNotification = animeDataList?.firstOrNull()?.isNotification ?: false
                    ModifyStateUI.Success(
                        anime = getAnimeInfoUseCase.invoke(anime_id = id),
                        isNotification = isNotification
                    )
                } catch (e: IOException) {
                    ModifyStateUI.Error
                } catch (e: HttpException) {
                    ModifyStateUI.Error
                }
            }
        }

    }

    fun addAnime(endDate: LocalDate , context: Context, selected : Boolean) {
        viewModelScope.launch {
                val anime = (_uiState.value as ModifyStateUI.Success).anime
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val startDate = LocalDate.parse(anime.start_date, formatter)
                val animeData = AnimeData(
                    id = anime.id,
                    dateEnd = endDate,
                    dateStart = startDate,
                    title = anime.title,
                    image = anime.main_picture.medium,
                    isNotification = selected
                )
                addAnimeUseCase.invoke(animeData)

            val dateEnd = if (selected) {
                val today = LocalDate.now()
                val targetDayOfWeekString = anime.broadcast.day_of_the_week
                val targetDayOfWeek = DayOfWeek.valueOf(targetDayOfWeekString.uppercase(Locale.getDefault()))
                val daysUntilTarget = (targetDayOfWeek.value - today.dayOfWeek.value + 7) % 7
                //today.plusDays(daysUntilTarget.toLong()).atStartOfDay(ZoneId.systemDefault()).plusHours(9).toInstant()
                LocalDateTime.now().plusSeconds(5).atZone(ZoneId.systemDefault()).toInstant()
            }else{
                endDate.atStartOfDay(ZoneId.systemDefault()).plusHours(9).toInstant()

            }
                NotificationHandler.scheduleNotification(context,anime.id,anime.title, dateEnd.toEpochMilli(),selected,endDate)

        }
    }
    fun addAnime(episodes: Int , context: Context,selected : Boolean) {
        viewModelScope.launch {
                val anime = (_uiState.value as ModifyStateUI.Success).anime
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val startDate = LocalDate.parse(anime.start_date, formatter)
                val endDate = startDate.plusWeeks(episodes.toLong()-1)
                val animeData = AnimeData(
                    id = anime.id,
                    dateEnd = endDate,
                    dateStart = startDate,
                    title = anime.title,
                    image = anime.main_picture.medium,
                    isNotification = selected
                )
                addAnimeUseCase.invoke(animeData)
            val dateEnd = if (selected) {
                val today = LocalDate.now()
                val targetDayOfWeekString = anime.broadcast.day_of_the_week
                val targetDayOfWeek = DayOfWeek.valueOf(targetDayOfWeekString.uppercase(Locale.getDefault()))
                val daysUntilTarget = (targetDayOfWeek.value - today.dayOfWeek.value + 7) % 7
                today.plusDays(daysUntilTarget.toLong()).atStartOfDay(ZoneId.systemDefault()).plusHours(9).toInstant()
            }else{
                endDate.atStartOfDay(ZoneId.systemDefault()).plusHours(9).toInstant()

            }
            NotificationHandler.scheduleNotification(context,anime.id,anime.title, dateEnd.toEpochMilli(),selected, endDate)
        }
    }

}