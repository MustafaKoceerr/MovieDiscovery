package com.example.moviediscovery.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviediscovery.domain.model.Resource
import com.example.moviediscovery.domain.usecase.GetLanguageUseCase
import com.example.moviediscovery.domain.usecase.GetNowPlayingMoviesUseCase
import com.example.moviediscovery.domain.usecase.GetPopularMoviesUseCase
import com.example.moviediscovery.domain.usecase.GetTopRatedMoviesUseCase
import com.example.moviediscovery.domain.usecase.GetUpcomingMoviesUseCase
import com.example.moviediscovery.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    getLanguageUseCase: GetLanguageUseCase
) : BaseViewModel(getLanguageUseCase) {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    init {
        loadMovies()
        observeLanguageChanges { loadMovies() }
    }

    fun processIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadMovies -> loadMovies()
            is HomeIntent.MovieClicked -> {} // Will be handled by navigation
        }
    }

    private fun loadMovies() {
        _state.value = _state.value.copy(isLoading = true)

        getNowPlayingMovies()
        getPopularMovies()
        getTopRatedMovies()
        getUpcomingMovies()
    }

    private fun getNowPlayingMovies() {
        getNowPlayingMoviesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        nowPlayingMovies = result.data ?: emptyList(),
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "An unexpected error occurred",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getPopularMovies() {
        getPopularMoviesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        popularMovies = result.data ?: emptyList(),
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "An unexpected error occurred",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getTopRatedMovies() {
        getTopRatedMoviesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        topRatedMovies = result.data ?: emptyList(),
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "An unexpected error occurred",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getUpcomingMovies() {
        getUpcomingMoviesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        upcomingMovies = result.data ?: emptyList(),
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "An unexpected error occurred",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}