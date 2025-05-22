package com.example.moviediscovery.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.moviediscovery.domain.usecase.GetLanguageUseCase
import com.example.moviediscovery.domain.usecase.GetNowPlayingMoviesUseCase
import com.example.moviediscovery.domain.usecase.GetPopularMoviesUseCase
import com.example.moviediscovery.domain.usecase.GetTopRatedMoviesUseCase
import com.example.moviediscovery.domain.usecase.GetUpcomingMoviesUseCase
import com.example.moviediscovery.presentation.base.BaseViewModel
import com.example.moviediscovery.presentation.common.pagination.PaginationController
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val nowPlayingController = PaginationController(viewModelScope) { paginationState ->
        _state.value = _state.value.copy(nowPlayingState = paginationState)
    }

    private val popularController = PaginationController(viewModelScope) { paginationState ->
        _state.value = _state.value.copy(popularState = paginationState)
    }

    private val topRatedController = PaginationController(viewModelScope) { paginationState ->
        _state.value = _state.value.copy(topRatedState = paginationState)
    }

    private val upcomingController = PaginationController(viewModelScope) { paginationState ->
        _state.value = _state.value.copy(upcomingState = paginationState)
    }

    init {
        loadInitialData()
        observeLanguageChanges {
            refreshAll()
        }
    }

    private fun loadInitialData() {
        _state.value = _state.value.copy(isInitialLoading = true, generalError = "")

        nowPlayingController.loadPage(1) { page -> getNowPlayingMoviesUseCase(page) }
        popularController.loadPage(1) { page -> getPopularMoviesUseCase(page) }
        topRatedController.loadPage(1) { page -> getTopRatedMoviesUseCase(page) }
        upcomingController.loadPage(1) { page -> getUpcomingMoviesUseCase(page) }

        _state.value = _state.value.copy(isInitialLoading = false)
    }

    private fun refreshAll() {
        _state.value = HomeState(isInitialLoading = true)

        nowPlayingController.refresh { page -> getNowPlayingMoviesUseCase(page) }
        popularController.refresh { page -> getPopularMoviesUseCase(page) }
        topRatedController.refresh { page -> getTopRatedMoviesUseCase(page) }
        upcomingController.refresh { page -> getUpcomingMoviesUseCase(page) }

        _state.value = _state.value.copy(isInitialLoading = false)
    }

    override fun onCleared() {
        super.onCleared()
        nowPlayingController.clear()
        popularController.clear()
        topRatedController.clear()
        upcomingController.clear()
    }

    fun processIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadMovies -> loadInitialData()
            is HomeIntent.MovieClicked -> {}

            is HomeIntent.LoadNextNowPlayingPage ->
                nowPlayingController.loadNextPage { page -> getNowPlayingMoviesUseCase(page) }
            is HomeIntent.RetryNowPlaying ->
                nowPlayingController.retry { page -> getNowPlayingMoviesUseCase(page) }

            is HomeIntent.LoadNextPopularPage ->
                popularController.loadNextPage { page -> getPopularMoviesUseCase(page) }
            is HomeIntent.RetryPopular ->
                popularController.retry { page -> getPopularMoviesUseCase(page) }

            is HomeIntent.LoadNextTopRatedPage ->
                topRatedController.loadNextPage { page -> getTopRatedMoviesUseCase(page) }
            is HomeIntent.RetryTopRated ->
                topRatedController.retry { page -> getTopRatedMoviesUseCase(page) }

            is HomeIntent.LoadNextUpcomingPage ->
                upcomingController.loadNextPage { page -> getUpcomingMoviesUseCase(page) }
            is HomeIntent.RetryUpcoming ->
                upcomingController.retry { page -> getUpcomingMoviesUseCase(page) }

            is HomeIntent.RefreshAll -> refreshAll()
        }
    }
}