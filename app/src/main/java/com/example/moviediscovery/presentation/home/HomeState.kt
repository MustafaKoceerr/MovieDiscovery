package com.example.moviediscovery.presentation.home

import com.example.moviediscovery.domain.model.Movie
import com.example.moviediscovery.presentation.common.pagination.MoviePaginationState
import java.lang.Error

data class HomeState(
    val nowPlayingState: MoviePaginationState = MoviePaginationState(),
    val popularState: MoviePaginationState = MoviePaginationState(),
    val topRatedState: MoviePaginationState = MoviePaginationState(),
    val upcomingState: MoviePaginationState = MoviePaginationState(),
    val isInitialLoading: Boolean = false,
    val generalError: String = ""
) {
    val isAnyLoading: Boolean
        get() = nowPlayingState.isLoading || popularState.isLoading ||
                topRatedState.isLoading || upcomingState.isLoading || isInitialLoading

    val hasAnyMovies: Boolean
        get() = nowPlayingState.items.isNotEmpty() || popularState.items.isNotEmpty() ||
                topRatedState.items.isNotEmpty() || upcomingState.items.isNotEmpty()

    val allEmpty: Boolean
        get() = nowPlayingState.items.isEmpty() && popularState.items.isEmpty() &&
                topRatedState.items.isEmpty() && upcomingState.items.isEmpty()

    val hasGlobalError:Boolean
        get()= generalError.isNotEmpty() && allEmpty && !isInitialLoading
}
