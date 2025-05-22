package com.example.moviediscovery.presentation.common.pagination

import com.example.moviediscovery.domain.model.Movie

data class PaginationState<T>(
    val items: List<T> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val currentPage: Int = 1,
    val endReached: Boolean = false,
)

typealias MoviePaginationState = PaginationState<Movie>