package com.example.moviediscovery.domain.usecase

import com.example.moviediscovery.domain.model.Movie
import com.example.moviediscovery.domain.model.Resource
import com.example.moviediscovery.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(query: String): Flow<Resource<List<Movie>>> {
        return movieRepository.searchMovies(query)
    }
}