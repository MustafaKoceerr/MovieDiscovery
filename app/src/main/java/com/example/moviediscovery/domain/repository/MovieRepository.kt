package com.example.moviediscovery.domain.repository

import com.example.moviediscovery.domain.model.Movie
import com.example.moviediscovery.domain.model.MovieDetails
import com.example.moviediscovery.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getNowPlayingMovies(): Flow<Resource<List<Movie>>>
    fun getPopularMovies(): Flow<Resource<List<Movie>>>
    fun getTopRatedMovies(): Flow<Resource<List<Movie>>>
    fun getUpcomingMovies(): Flow<Resource<List<Movie>>>
    fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetails>>
    fun searchMovies(query: String): Flow<Resource<List<Movie>>>
}