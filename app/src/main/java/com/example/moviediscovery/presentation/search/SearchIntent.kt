package com.example.moviediscovery.presentation.search

sealed class SearchIntent {
    data class QueryChanged(val query: String) : SearchIntent()
    data class MovieClicked(val movieId: Int) : SearchIntent()
    object SearchMovies : SearchIntent()

    object LoadNextPage : SearchIntent()
    object Retry : SearchIntent()
    object RefreshSearch : SearchIntent()
}