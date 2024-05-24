package com.example.twit.ui.screens.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twit.domain.database.GetAnimeUseCase
import com.example.twit.domain.network.getAnimeInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor( private val getAnimeUseCase: GetAnimeUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<CalendarStateUI>(CalendarStateUI.Loading)
    val uiState: StateFlow<CalendarStateUI> = _uiState.asStateFlow()
    init {
        viewModelScope.launch {
            _uiState.value = CalendarStateUI.Loading
            try {
                getAnimeUseCase().collect {
                    _uiState.value = CalendarStateUI.Success(it)
                }
            } catch (e: Exception) {
                _uiState.value = CalendarStateUI.Error
            }

        }

    }
}