package com.example.moviediscovery.presentation.home.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviediscovery.domain.model.Movie
import com.example.moviediscovery.presentation.common.MovieItem
import com.example.moviediscovery.presentation.home.sampleMovies
import com.example.moviediscovery.presentation.theme.MovieDiscoveryTheme

private val TAG: String = "MOVIE"

@Composable
fun MovieSection(
    title: String,
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(top = 16.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(movies) { movie ->
                MovieItem(
                    movie = movie,
                    onMovieClick = onMovieClick
                )
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun MovieSectionPreview() {
    MovieDiscoveryTheme {
        MovieSection(
            "Favorite Movies",
            sampleMovies + sampleMovies,
            { movieId ->
                Log.d(TAG, "film id: $movieId")
            }
        )
    }
}
