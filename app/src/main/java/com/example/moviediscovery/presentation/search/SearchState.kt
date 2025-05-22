package com.example.moviediscovery.presentation.search

import com.example.moviediscovery.domain.model.Movie

data class SearchState(
    val query: String = "",
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",

    // Pagination specific
    val currentPage: Int = 1,
    val endReached: Boolean = false,
    val isLoadingMore: Boolean = false
)