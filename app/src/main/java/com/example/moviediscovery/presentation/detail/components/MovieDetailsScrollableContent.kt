package com.example.moviediscovery.presentation.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.moviediscovery.domain.model.MovieDetails
import com.example.moviediscovery.presentation.detail.sampleMovieDetailsList
import com.example.moviediscovery.presentation.theme.MovieDiscoveryTheme

// Scrollable content container
@Composable
fun MovieDetailsScrollableContent(
    movieDetails: MovieDetails,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        MovieHeader(
            title = movieDetails.title,
            backdropPath = movieDetails.backdropPath,
            posterPath = movieDetails.posterPath
        )

        MovieInfo(
            movieDetails = movieDetails
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailsScrollableContentPreview() {
    MovieDiscoveryTheme {
        MovieDetailsScrollableContent(sampleMovieDetailsList[0])
    }
}