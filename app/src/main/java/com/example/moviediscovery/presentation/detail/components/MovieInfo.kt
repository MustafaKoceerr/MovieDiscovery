package com.example.moviediscovery.presentation.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviediscovery.domain.model.MovieDetails
import com.example.moviediscovery.presentation.detail.sampleMovieDetailsList
import com.example.moviediscovery.presentation.theme.MovieDiscoveryTheme

@Composable
fun MovieInfo(
    movieDetails: MovieDetails,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 80.dp) // Account for poster overlap
    ) {
        // title and rating
        TitleAndRating(
            title = movieDetails.title,
            voteAverage = movieDetails.voteAverage
        )

        // Release date and runtime
        ReleaseInfoRow(
            releaseDate = movieDetails.releaseDate,
            runtime = movieDetails.runtime
        )

        // Genres
        if (movieDetails.genres.isNotEmpty()) {
            GenreChips(genres = movieDetails.genres)
        }

        // TagLine
        if (!movieDetails.tagline.isNullOrEmpty()) {
            TagLine(tagline = movieDetails.tagline)
        }

        // Overview
        OverviewSection(overview = movieDetails.overview)
    }
}

@Preview(showBackground = true)
@Composable
fun MovieInfoPreview() {
    MovieDiscoveryTheme {
        MovieInfo(sampleMovieDetailsList[0])
    }
}