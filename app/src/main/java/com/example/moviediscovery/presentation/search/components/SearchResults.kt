package com.example.moviediscovery.presentation.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviediscovery.domain.model.Movie
import com.example.moviediscovery.presentation.common.MovieItem
import com.example.moviediscovery.presentation.home.sampleMovies
import com.example.moviediscovery.presentation.theme.MovieDiscoveryTheme

@Composable
fun SearchResults(
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(movies) { movie ->
            MovieItem(
                movie = movie,
                onMovieClick = { movieId ->
                    onMovieClick(movieId)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchResults() {
    MovieDiscoveryTheme {
        SearchResults(sampleMovies + sampleMovies+ sampleMovies) { }
    }
}