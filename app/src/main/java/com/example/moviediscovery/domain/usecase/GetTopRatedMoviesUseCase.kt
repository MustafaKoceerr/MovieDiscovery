package com.example.moviediscovery.domain.usecase

import com.example.moviediscovery.domain.model.Movie
import com.example.moviediscovery.domain.model.Resource
import com.example.moviediscovery.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(page: Int = 1): Flow<Resource<List<Movie>>> {
        return movieRepository.getTopRatedMovies(page)
    }
}