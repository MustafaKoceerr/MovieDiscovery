package com.example.moviediscovery.presentation.home

import com.example.moviediscovery.presentation.common.pagination.MoviePaginationState

data class HomeState(
    val nowPlayingState: MoviePaginationState = MoviePaginationState(),
    val popularState: MoviePaginationState = MoviePaginationState(),
    val topRatedState: MoviePaginationState = MoviePaginationState(),
    val upcomingState: MoviePaginationState = MoviePaginationState(),
    val isInitialLoading: Boolean = false,
    val generalError: String = ""
) {

    val allEmpty: Boolean
        get() = nowPlayingState.items.isEmpty() && popularState.items.isEmpty() &&
                topRatedState.items.isEmpty() && upcomingState.items.isEmpty()

    val hasGlobalError:Boolean
        get()= generalError.isNotEmpty() && allEmpty && !isInitialLoading
}
