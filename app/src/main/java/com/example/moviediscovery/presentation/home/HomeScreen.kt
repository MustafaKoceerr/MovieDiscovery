package com.example.moviediscovery.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moviediscovery.domain.model.Movie
import com.example.moviediscovery.presentation.common.ErrorView
import com.example.moviediscovery.presentation.common.LoadingView
import com.example.moviediscovery.presentation.home.components.HomeContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMovieClick: (Int) -> Unit,
    onSearchClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            // Show global loading only if all categories are empty and initially loading
            state.isInitialLoading && state.allEmpty -> {
                LoadingView() // Your cinematic loading screen
            }

            // Show global error only if all categories are empty and have errors
            state.hasGlobalError -> {
                ErrorView(message = state.generalError) {
                    viewModel.processIntent(HomeIntent.RefreshAll)
                }
            }

            // Show content with individual section states
            else -> {
                HomeContent(
                    state = state,
                    onMovieClick = { movieId ->
                        onMovieClick(movieId)
                        viewModel.processIntent(HomeIntent.MovieClicked(movieId))
                    },
                    onSearchClick = onSearchClick,
                    onIntent = viewModel::processIntent
                )
            }
        }
    }
}

// Keep the sample movies for previews
val sampleMovies = listOf(
    Movie(
        id = 1,
        title = "The Dark Knight",
        overview = "When the menace known as the Joker emerges, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
        posterPath = "/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
        backdropPath = "/hqkIcbrOHL86UncnHIsHVcVmzue.jpg",
        releaseDate = "2008-07-16",
        voteAverage = 8.5,
        genreIds = listOf(28, 80, 18),
        popularity = 150.0
    ),
    Movie(
        id = 2,
        title = "Inception",
        overview = "A thief who steals corporate secrets through dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO.",
        posterPath = "/edv5CZvWj09upOsy2Y6IwDhK8bt.jpg",
        backdropPath = "/s3TBrRGB1iav7gFOCNx3H31MoES.jpg",
        releaseDate = "2010-07-15",
        voteAverage = 8.3,
        genreIds = listOf(28, 878, 12),
        popularity = 170.0
    ),
    Movie(
        id = 3,
        title = "Interstellar",
        overview = "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
        posterPath = "/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg",
        backdropPath = "/rAiYTfKGqDCRIIqo664sY9XZIvQ.jpg",
        releaseDate = "2014-11-05",
        voteAverage = 8.4,
        genreIds = listOf(12, 18, 878),
        popularity = 160.0
    )
)