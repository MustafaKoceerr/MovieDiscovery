package com.example.moviediscovery.presentation.home

import com.example.moviediscovery.domain.model.Movie
import java.lang.Error

data class HomeState(
    // Movie lists
    val nowPlayingMovies: List<Movie> = emptyList(),
    val popularMovies: List<Movie> = emptyList(),
    val topRatedMovies: List<Movie> = emptyList(),
    val upcomingMovies: List<Movie> = emptyList(),

    // Loading states for each category
    val isNowPlayingLoading: Boolean = false,
    val isPopularLoading: Boolean = false,
    val isTopRatedLoading: Boolean = false,
    val isUpcomingLoading: Boolean = false,

    // Error states for each category
    val nowPlayingError: String = "",
    val popularError: String = "",
    val topRatedError: String = "",
    val upcomingError: String = "",

    // Pagination states for each category
    val nowPlayingPage: Int = 1,
    val popularPage: Int = 1,
    val topRatedPage: Int = 1,
    val upcomingPage: Int = 1,

    val nowPlayingEndReached: Boolean = false,
    val popularEndReached: Boolean = false,
    val topRatedEndReached: Boolean = false,
    val upcomingEndReached: Boolean = false,

    // General loading state (for initial load)
    val isLoading: Boolean = false,
    val error: String = ""
)
