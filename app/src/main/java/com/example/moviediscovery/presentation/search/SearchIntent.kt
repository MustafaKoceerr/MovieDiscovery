package com.example.moviediscovery.presentation.search

import retrofit2.http.Query

sealed class SearchIntent {
    data class QueryChanged(val query: String) : SearchIntent()
    data class MovieClicked(val movieId: Int) : SearchIntent()
    object SearchMovies : SearchIntent()
}