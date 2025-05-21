package com.example.moviediscovery.presentation.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moviediscovery.domain.model.Genre
import com.example.moviediscovery.domain.model.MovieDetails
import com.example.moviediscovery.presentation.common.ErrorView
import com.example.moviediscovery.presentation.common.LoadingView
import com.example.moviediscovery.presentation.detail.components.MovieDetailsContent

@Composable
fun DetailScreen(
    movieId: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state by viewModel.state

    // Ensure the movieId is used and the details are loaded
    DisposableEffect(movieId) {
        viewModel.setMovieId(movieId)
        viewModel.processIntent(DetailIntent.LoadMovieDetails)
        onDispose { }
    }

    Box(modifier = modifier.fillMaxSize()) {
        when {
            state.isLoading -> {
                LoadingView()
            }

            state.error.isNotEmpty() -> {
                ErrorView(
                    message = state.error,
                    onRetry = {
                        viewModel.processIntent(DetailIntent.LoadMovieDetails)
                    }
                )
            }

            state.movieDetails != null -> {
                MovieDetailsContent(
                    movieDetails = state.movieDetails!!,
                    onBackClick = onBackClick
                )
            }
        }
    }
}

val sampleMovieDetailsList = listOf(
    MovieDetails(
        id = 1,
        title = "The Dark Knight",
        overview = "When the menace known as the Joker emerges, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
        posterPath = "/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
        backdropPath = "/hqkIcbrOHL86UncnHIsHVcVmzue.jpg",
        releaseDate = "2008-07-16",
        voteAverage = 8.5,
        runtime = 152,
        genres = listOf(
            Genre(id = 28, name = "Action"),
            Genre(id = 80, name = "Crime"),
            Genre(id = 18, name = "Drama")
        ),
        popularity = 150.0,
        tagline = "Why So Serious?"
    ),
    MovieDetails(
        id = 2,
        title = "Inception",
        overview = "A thief who steals corporate secrets through dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO.",
        posterPath = "/edv5CZvWj09upOsy2Y6IwDhK8bt.jpg",
        backdropPath = "/s3TBrRGB1iav7gFOCNx3H31MoES.jpg",
        releaseDate = "2010-07-15",
        voteAverage = 8.3,
        runtime = 148,
        genres = listOf(
            Genre(id = 28, name = "Action"),
            Genre(id = 878, name = "Science Fiction"),
            Genre(id = 12, name = "Adventure")
        ),
        popularity = 170.0,
        tagline = "Your mind is the scene of the crime."
    ),
    MovieDetails(
        id = 3,
        title = "Interstellar",
        overview = "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
        posterPath = "/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg",
        backdropPath = "/rAiYTfKGqDCRIIqo664sY9XZIvQ.jpg",
        releaseDate = "2014-11-05",
        voteAverage = 8.4,
        runtime = 169,
        genres = listOf(
            Genre(id = 12, name = "Adventure"),
            Genre(id = 18, name = "Drama"),
            Genre(id = 878, name = "Science Fiction")
        ),
        popularity = 160.0,
        tagline = "Mankind was born on Earth. It was never meant to die here."
    )
)


