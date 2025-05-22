package com.example.moviediscovery.presentation.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviediscovery.domain.model.Movie
import com.example.moviediscovery.presentation.common.MovieItem
import com.example.moviediscovery.presentation.home.sampleMovies
import com.example.moviediscovery.presentation.theme.MovieDiscoveryTheme
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun SearchResults(
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit,
    onLoadMore: () -> Unit,
    onRetry: () -> Unit,
    isLoadingMore: Boolean = false,
    error: String = "",
    endReached: Boolean = false
) {
    val gridState = rememberLazyGridState()

    // Detect when user scrolls to the end
    LaunchedEffect(gridState, movies.size) {
        snapshotFlow { gridState.layoutInfo }
            .map { layoutInfo ->
                val visibleItems = layoutInfo.visibleItemsInfo
                val totalItems = layoutInfo.totalItemsCount
                if (visibleItems.isNotEmpty() && totalItems > 0) {
                    val lastVisibleItem = visibleItems.last().index
                    lastVisibleItem >= totalItems - 4 // Load when 4 items before end
                } else false
            }
            .distinctUntilChanged()
            .collect { isNearEnd ->
                if (isNearEnd && !isLoadingMore && !endReached && error.isEmpty()) {
                    onLoadMore()
                }
            }
    }

    LazyVerticalGrid(
        state = gridState,
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

        // Loading/Error item at the end (spans both columns)
        item {
            when {
                isLoadingMore -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                error.isNotEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Error loading more movies",
                                color = Color.Red,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Tap to retry",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .clickable { onRetry() }
                                    .padding(8.dp)
                            )
                        }
                    }
                }

                endReached && movies.isNotEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No more movies to load",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchResultsPaginationPreview() {
    MovieDiscoveryTheme {
        SearchResults(
            movies = sampleMovies + sampleMovies + sampleMovies,
            onMovieClick = { },
            onLoadMore = { },
            onRetry = { },
            isLoadingMore = true
        )
    }
}