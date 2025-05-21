package com.example.moviediscovery.presentation.home

import com.example.moviediscovery.domain.model.Movie
import java.lang.Error

data class HomeState(
    val nowPlayingMovies: List<Movie> = emptyList(),
    val popularMovies: List<Movie> = emptyList(),
    val topRatedMovies: List<Movie> = emptyList(),
    val upcomingMovies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
