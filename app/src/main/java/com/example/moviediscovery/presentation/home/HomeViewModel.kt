package com.example.moviediscovery.presentation.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
        observeLanguageChanges {
            // Reset and reload when language changes
            refreshAll()
        }
    }

    fun processIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadMovies -> loadMovies()
            is HomeIntent.MovieClicked -> {} // Will be handled by navigation

            // Now Playing pagination
            is HomeIntent.LoadNextNowPlayingPage -> loadNextNowPlayingPage()
            is HomeIntent.RetryNowPlaying -> retryNowPlaying()

            // Popular pagination
            is HomeIntent.LoadNextPopularPage -> loadNextPopularPage()
            is HomeIntent.RetryPopular -> retryPopular()

            // Top Rated pagination
            is HomeIntent.LoadNextTopRatedPage -> loadNextTopRatedPage()
            is HomeIntent.RetryTopRated -> retryTopRated()

            // Upcoming pagination
            is HomeIntent.LoadNextUpcomingPage -> loadNextUpcomingPage()
            is HomeIntent.RetryUpcoming -> retryUpcoming()

            // Refresh
            is HomeIntent.RefreshAll -> refreshAll()
        }
    }

    private fun loadMovies() {
        _state.value = _state.value.copy(isLoading = true)
        loadNowPlayingMovies(1)
        loadPopularMovies(1)
        loadTopRatedMovies(1)
        loadUpcomingMovies(1)
    }

    private fun refreshAll() {
        _state.value = HomeState(isLoading = true)
        loadMovies()
    }

    // Now Playing Movies
    private fun loadNowPlayingMovies(page: Int) {
        // Prevent multiple concurrent requests for the same page
        if (_state.value.isNowPlayingLoading) return

        _state.value = _state.value.copy(
            isNowPlayingLoading = true,
            nowPlayingError = ""
        )

        getNowPlayingMoviesUseCase(page).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val newMovies = result.data ?: emptyList()
                    val allMovies = if (page == 1) {
                        newMovies
                    } else {
                        _state.value.nowPlayingMovies + newMovies
                    }

                    _state.value = _state.value.copy(
                        nowPlayingMovies = allMovies,
                        isNowPlayingLoading = false,
                        nowPlayingError = "",
                        nowPlayingPage = page,
                        nowPlayingEndReached = newMovies.isEmpty(),
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        nowPlayingError = result.message ?: "An unexpected error occurred",
                        isNowPlayingLoading = false,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    // Loading state already set above
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun loadNextNowPlayingPage() {
        if (!_state.value.nowPlayingEndReached && !_state.value.isNowPlayingLoading) {
            val nextPage = _state.value.nowPlayingPage + 1
            loadNowPlayingMovies(nextPage)
        }
    }

    private fun retryNowPlaying() {
        if (!_state.value.isNowPlayingLoading) {
            val pageToRetry = if (_state.value.nowPlayingMovies.isEmpty()) 1 else _state.value.nowPlayingPage
            loadNowPlayingMovies(pageToRetry)
        }
    }

    // Popular Movies
    private fun loadPopularMovies(page: Int) {
        // Prevent multiple concurrent requests for the same page
        if (_state.value.isPopularLoading) return

        _state.value = _state.value.copy(
            isPopularLoading = true,
            popularError = ""
        )

        getPopularMoviesUseCase(page).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val newMovies = result.data ?: emptyList()
                    val allMovies = if (page == 1) {
                        newMovies
                    } else {
                        _state.value.popularMovies + newMovies
                    }

                    _state.value = _state.value.copy(
                        popularMovies = allMovies,
                        isPopularLoading = false,
                        popularError = "",
                        popularPage = page,
                        popularEndReached = newMovies.isEmpty(),
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        popularError = result.message ?: "An unexpected error occurred",
                        isPopularLoading = false,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    // Loading state already set above
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun loadNextPopularPage() {
        if (!_state.value.popularEndReached && !_state.value.isPopularLoading) {
            val nextPage = _state.value.popularPage + 1
            loadPopularMovies(nextPage)
        }
    }

    private fun retryPopular() {
        if (!_state.value.isPopularLoading) {
            val pageToRetry = if (_state.value.popularMovies.isEmpty()) 1 else _state.value.popularPage
            loadPopularMovies(pageToRetry)
        }
    }

    // Top Rated Movies
    private fun loadTopRatedMovies(page: Int) {
        // Prevent multiple concurrent requests for the same page
        if (_state.value.isTopRatedLoading) return

        _state.value = _state.value.copy(
            isTopRatedLoading = true,
            topRatedError = ""
        )

        getTopRatedMoviesUseCase(page).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val newMovies = result.data ?: emptyList()
                    val allMovies = if (page == 1) {
                        newMovies
                    } else {
                        _state.value.topRatedMovies + newMovies
                    }

                    _state.value = _state.value.copy(
                        topRatedMovies = allMovies,
                        isTopRatedLoading = false,
                        topRatedError = "",
                        topRatedPage = page,
                        topRatedEndReached = newMovies.isEmpty(),
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        topRatedError = result.message ?: "An unexpected error occurred",
                        isTopRatedLoading = false,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    // Loading state already set above
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun loadNextTopRatedPage() {
        if (!_state.value.topRatedEndReached && !_state.value.isTopRatedLoading) {
            val nextPage = _state.value.topRatedPage + 1
            loadTopRatedMovies(nextPage)
        }
    }

    private fun retryTopRated() {
        if (!_state.value.isTopRatedLoading) {
            val pageToRetry = if (_state.value.topRatedMovies.isEmpty()) 1 else _state.value.topRatedPage
            loadTopRatedMovies(pageToRetry)
        }
    }

    // Upcoming Movies
    private fun loadUpcomingMovies(page: Int) {
        // Prevent multiple concurrent requests for the same page
        if (_state.value.isUpcomingLoading) return

        _state.value = _state.value.copy(
            isUpcomingLoading = true,
            upcomingError = ""
        )

        getUpcomingMoviesUseCase(page).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val newMovies = result.data ?: emptyList()
                    val allMovies = if (page == 1) {
                        newMovies
                    } else {
                        _state.value.upcomingMovies + newMovies
                    }

                    _state.value = _state.value.copy(
                        upcomingMovies = allMovies,
                        isUpcomingLoading = false,
                        upcomingError = "",
                        upcomingPage = page,
                        upcomingEndReached = newMovies.isEmpty(),
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        upcomingError = result.message ?: "An unexpected error occurred",
                        isUpcomingLoading = false,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    // Loading state already set above
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun loadNextUpcomingPage() {
        if (!_state.value.upcomingEndReached && !_state.value.isUpcomingLoading) {
            val nextPage = _state.value.upcomingPage + 1
            loadUpcomingMovies(nextPage)
        }
    }

    private fun retryUpcoming() {
        if (!_state.value.isUpcomingLoading) {
            val pageToRetry = if (_state.value.upcomingMovies.isEmpty()) 1 else _state.value.upcomingPage
            loadUpcomingMovies(pageToRetry)
        }
    }
}