package com.example.moviediscovery.presentation.detail

import com.example.moviediscovery.domain.model.MovieDetails

data class DetailState(
    val movieDetails: MovieDetails? = null,
    val isLoading: Boolean = false,
    var error: String = ""
)