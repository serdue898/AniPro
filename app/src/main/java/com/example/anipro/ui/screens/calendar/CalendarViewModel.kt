package com.example.anipro.ui.screens.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anipro.domain.database.GetAnimeUseCase
import com.example.anipro.utils.NotificationHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getAnimeUseCase: GetAnimeUseCase

) : ViewModel() {
    private val _uiState = MutableStateFlow<CalendarStateUI>(CalendarStateUI.Loading)
    val uiState: StateFlow<CalendarStateUI> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = CalendarStateUI.Loading
            try {
                getAnimeUseCase().collect {
                    _uiState.value = CalendarStateUI.Success(it, it.filter { anime-> 
                        val fotmatter = anime.dateEnd
                        fotmatter.isEqual(LocalDate.now())
                    } )
                }
            } catch (e: Exception) {
                _uiState.value = CalendarStateUI.Error
            }

        }

    }
    fun onDateSelected(date:LocalDate) {
        val animes = (_uiState.value as CalendarStateUI.Success).animeList
        val animesShowList = animes.filter { anime ->
            val formatter = anime.dateEnd
            formatter.isEqual(date)
        }
        _uiState.value = CalendarStateUI.Success(animes, animesShowList)
    }
}