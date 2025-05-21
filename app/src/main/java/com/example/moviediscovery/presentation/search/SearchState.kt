package com.example.moviediscovery.presentation.search

import com.example.moviediscovery.domain.model.Movie

data class SearchState(
    val query: String = "",
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)