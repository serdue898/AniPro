package com.example.anipro.ui.screens.search


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anipro.domain.database.GetAnimeUseCase
import com.example.anipro.domain.network.SearchAnimeUseCase
import com.example.anipro.domain.network.getAnimeRankingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getTwitUseCase: GetAnimeUseCase,
    private val getAnimeUseCase: SearchAnimeUseCase,
    private val getAnimeRankingUseCase: getAnimeRankingUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<SearchStateUI>(SearchStateUI.Loading)
    val uiState: StateFlow<SearchStateUI> = _uiState.asStateFlow()

    init {
        getAnimes()
    }

    private fun getAnimes() {
        viewModelScope.launch {
            _uiState.update { SearchStateUI.Loading }
            _uiState.update {
                try {
                    val res = getAnimeRankingUseCase()
                    SearchStateUI.Succes(animes = res)
                } catch (e: IOException) {
                    SearchStateUI.Error
                } catch (e: HttpException) {
                    SearchStateUI.Error
                }
            }
        }

    }

    fun onToogleSearch() {
        if (_uiState.value is SearchStateUI.Succes) {
            _uiState.update { current ->
                (current as SearchStateUI.Succes).copy(isSearching = !current.isSearching)
            }
        }
    }

    fun onSearchTextChange(s: String) {
        _uiState.update { current ->
            (current as SearchStateUI.Succes).copy(search = s)
        }
        searchNames(s)
    }

    private fun searchNames(s: String) {
        viewModelScope.launch {
            if (_uiState.value is SearchStateUI.Succes) {
                var res2 = (uiState.value as SearchStateUI.Succes).searchResult
                if (s.length >= 3) {
                    res2 = getAnimeUseCase(s)
                }
                _uiState.update { current ->
                    (current as SearchStateUI.Succes).copy(searchResult = res2, search = s)

                }
            }
        }
    }

    fun onSearch(s: String) {
        viewModelScope.launch {
            _uiState.update {
                try {
                    var res = (uiState.value as SearchStateUI.Succes).animes
                    if (s.length >= 3)
                        res = getAnimeUseCase(s)
                    SearchStateUI.Succes(animes = res, searchResult = res, search = s)
                } catch (e: IOException) {
                    SearchStateUI.Error
                } catch (e: HttpException) {
                    SearchStateUI.Error
                }
            }
        }
    }
}