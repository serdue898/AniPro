package com.example.twit.ui.calendar

import androidx.lifecycle.ViewModel
import com.example.twit.domain.network.getAnimeInfoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class CalendarViewModel @Inject constructor(
    private val getAnimeInfoUseCase: getAnimeInfoUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<CalendarStateUI>(CalendarStateUI.Loading)
    val uiState: StateFlow<CalendarStateUI> = _uiState.asStateFlow()




}