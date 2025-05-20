package com.example.moviediscovery.domain.usecase

import com.example.moviediscovery.domain.model.MovieDetails
import com.example.moviediscovery.domain.model.Resource
import com.example.moviediscovery.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(movieId: Int): Flow<Resource<MovieDetails>> {
        return movieRepository.getMovieDetails(movieId)
    }
}