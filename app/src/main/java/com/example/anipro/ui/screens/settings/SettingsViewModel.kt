package com.example.anipro.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anipro.data.dataStore.DataStore
import com.example.anipro.ui.screens.modifyAnime.ModifyStateUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: DataStore
) : ViewModel(){
    private val _uiState = MutableStateFlow<SettingsStateUI>(SettingsStateUI.Loading)
    val uiState: StateFlow<SettingsStateUI> = _uiState.asStateFlow()



    fun saveTheme(value: String) {
        if (_uiState.value is SettingsStateUI.Success){
            _uiState.update {
                SettingsStateUI.Success(
                    theme = value,
                    color = (_uiState.value as SettingsStateUI.Success).color,
                    grid = (_uiState.value as SettingsStateUI.Success).grid
                )
            }

        }
        viewModelScope.launch {
            dataStore.saveTheme(value)
        }
    }

    fun saveColorTheme(value: String) {
        if (_uiState.value is SettingsStateUI.Success){
            _uiState.update {
                SettingsStateUI.Success(
                    theme = (_uiState.value as SettingsStateUI.Success).theme,
                    color = value,
                    grid = (_uiState.value as SettingsStateUI.Success).grid
                )
            }

        }
        viewModelScope.launch {
            dataStore.saveColorTheme(value)
        }
    }

    fun saveGridType(value: String) {
        if (_uiState.value is SettingsStateUI.Success){
            _uiState.update {
                SettingsStateUI.Success(
                    theme = (_uiState.value as SettingsStateUI.Success).theme,
                    color = (_uiState.value as SettingsStateUI.Success).color,
                    grid = value
                )
            }

        }
        viewModelScope.launch {
            dataStore.saveGridType(value)
        }
    }

    fun getSettings() {
        viewModelScope.launch {
            _uiState.update {
                SettingsStateUI.Success(
                    theme = dataStore.getTheme().first(),
                    color = dataStore.getColorTheme().first(),
                    grid = dataStore.getGridType().first()
                )
            }
        }
    }


}