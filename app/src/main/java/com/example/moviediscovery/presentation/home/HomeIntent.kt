package com.example.moviediscovery.presentation.home

sealed class HomeIntent {
    object LoadMovies : HomeIntent()
    data class MovieClicked(val movieId: Int) : HomeIntent()

    // Pagination events for each category
    object LoadNextNowPlayingPage : HomeIntent()
    object LoadNextPopularPage : HomeIntent()
    object LoadNextTopRatedPage : HomeIntent()
    object LoadNextUpcomingPage : HomeIntent()

    // Retry events for each category
    object RetryNowPlaying : HomeIntent()
    object RetryPopular : HomeIntent()
    object RetryTopRated : HomeIntent()
    object RetryUpcoming : HomeIntent()

    // Refresh events
    object RefreshAll : HomeIntent()
}