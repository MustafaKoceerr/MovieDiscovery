package com.example.moviediscovery.presentation.search

import com.example.moviediscovery.domain.model.Movie
import com.example.moviediscovery.presentation.common.pagination.MoviePaginationState
import com.example.moviediscovery.presentation.common.pagination.PaginationState

data class SearchState(
    val query: String = "",
    val paginationState: MoviePaginationState = MoviePaginationState(),
    val isInitialSearch: Boolean = false
) {
    // Computed properties for easier access
    val hasResults: Boolean
        get() = paginationState.items.isNotEmpty()

    val isEmptyQuery: Boolean
        get() = query.trim().isEmpty()

    val shouldShowInitialState: Boolean
        get() = isEmptyQuery && !hasResults

    val shouldShowEmptyResults: Boolean
        get() = !isEmptyQuery && !hasResults && !paginationState.isLoading && paginationState.error.isEmpty()

    val shouldShowResults: Boolean
        get() = hasResults

    val shouldShowGlobalError: Boolean
        get() = !hasResults && paginationState.error.isNotEmpty() && !paginationState.isLoading
}