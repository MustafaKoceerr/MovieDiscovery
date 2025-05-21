package com.example.moviediscovery.data.repository

import com.example.moviediscovery.BuildConfig
import com.example.moviediscovery.data.api.MovieApi
import com.example.moviediscovery.data.api.model.ApiResponse
import com.example.moviediscovery.data.api.model.MovieDetailsDto
import com.example.moviediscovery.data.api.model.MovieDto
import com.example.moviediscovery.data.api.model.toDomainModel
import com.example.moviediscovery.data.util.safeApiCall
import com.example.moviediscovery.domain.model.Movie
import com.example.moviediscovery.domain.model.MovieDetails
import com.example.moviediscovery.domain.model.Resource
import com.example.moviediscovery.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi
) : MovieRepository {

    override fun getNowPlayingMovies(): Flow<Resource<List<Movie>>> {
        return safeApiCall<ApiResponse<MovieDto>>(
            apiCall = { movieApi.getNowPlayingMovies(apiKey = BuildConfig.API_KEY) }
        ).map { resource: Resource<ApiResponse<MovieDto>> ->
            when (resource) {
                is Resource.Success -> {
                    val movies = resource.data.results.map { movieDto -> movieDto.toDomainModel() }
                    Resource.Success(movies)
                }
                is Resource.Error -> Resource.Error(resource.message, resource.code)
                is Resource.Loading -> Resource.Loading
            }
        }
    }

    override fun getPopularMovies(): Flow<Resource<List<Movie>>> {
        return safeApiCall<ApiResponse<MovieDto>>(
            apiCall = { movieApi.getPopularMovies(apiKey = BuildConfig.API_KEY) }
        ).map { resource: Resource<ApiResponse<MovieDto>> ->
            when (resource) {
                is Resource.Success -> {
                    val movies = resource.data.results.map { movieDto -> movieDto.toDomainModel() }
                    Resource.Success(movies)
                }
                is Resource.Error -> Resource.Error(resource.message, resource.code)
                is Resource.Loading -> Resource.Loading
            }
        }
    }

    override fun getTopRatedMovies(): Flow<Resource<List<Movie>>> {
        return safeApiCall<ApiResponse<MovieDto>>(
            apiCall = { movieApi.getTopRatedMovies(apiKey = BuildConfig.API_KEY) }
        ).map { resource: Resource<ApiResponse<MovieDto>> ->
            when (resource) {
                is Resource.Success -> {
                    val movies = resource.data.results.map { movieDto -> movieDto.toDomainModel() }
                    Resource.Success(movies)
                }
                is Resource.Error -> Resource.Error(resource.message, resource.code)
                is Resource.Loading -> Resource.Loading
            }
        }
    }

    override fun getUpcomingMovies(): Flow<Resource<List<Movie>>> {
        return safeApiCall<ApiResponse<MovieDto>>(
            apiCall = { movieApi.getUpcomingMovies(apiKey = BuildConfig.API_KEY) }
        ).map { resource: Resource<ApiResponse<MovieDto>> ->
            when (resource) {
                is Resource.Success -> {
                    val movies = resource.data.results.map { movieDto -> movieDto.toDomainModel() }
                    Resource.Success(movies)
                }
                is Resource.Error -> Resource.Error(resource.message, resource.code)
                is Resource.Loading -> Resource.Loading
            }
        }
    }

    override fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetails>> {
        return safeApiCall<MovieDetailsDto>(
            apiCall = { movieApi.getMovieDetails(movieId = movieId, apiKey = BuildConfig.API_KEY) }
        ).map { resource: Resource<MovieDetailsDto> ->
            when (resource) {
                is Resource.Success -> Resource.Success(resource.data.toDomainModel())
                is Resource.Error -> Resource.Error(resource.message, resource.code)
                is Resource.Loading -> Resource.Loading
            }
        }
    }

    override fun searchMovies(query: String): Flow<Resource<List<Movie>>> {
        return safeApiCall<ApiResponse<MovieDto>>(
            apiCall = { movieApi.searchMovies(apiKey = BuildConfig.API_KEY, query = query) }
        ).map { resource: Resource<ApiResponse<MovieDto>> ->
            when (resource) {
                is Resource.Success -> {
                    val movies = resource.data.results.map { movieDto -> movieDto.toDomainModel() }
                    Resource.Success(movies)
                }
                is Resource.Error -> Resource.Error(resource.message, resource.code)
                is Resource.Loading -> Resource.Loading
            }
        }
    }
}