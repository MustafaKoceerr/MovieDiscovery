package com.example.moviediscovery.domain.repository

import com.example.moviediscovery.domain.model.Movie
import com.example.moviediscovery.domain.model.MovieDetails
import com.example.moviediscovery.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getNowPlayingMovies(page: Int = 1): Flow<Resource<List<Movie>>>
    fun getPopularMovies(page: Int = 1): Flow<Resource<List<Movie>>>
    fun getTopRatedMovies(page: Int = 1): Flow<Resource<List<Movie>>>
    fun getUpcomingMovies(page: Int = 1): Flow<Resource<List<Movie>>>
    fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetails>>
    fun searchMovies(query: String, page: Int = 1): Flow<Resource<List<Movie>>>
}
