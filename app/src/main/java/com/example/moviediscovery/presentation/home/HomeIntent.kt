package com.example.moviediscovery.presentation.home

sealed class HomeIntent {
    object LoadMovies : HomeIntent()
    data class MovieClicked(val movieId: Int) : HomeIntent()
}