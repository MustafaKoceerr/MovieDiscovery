package com.example.moviediscovery.presentation.home.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.moviediscovery.domain.model.Movie
import com.example.moviediscovery.presentation.common.MovieItem
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

private const val TAG = "PaginatedMovieSection"

@Composable
fun PaginatedMovieSection(
    title: String,
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit,
    onLoadMore: () -> Unit,
    onRetry: () -> Unit,
    isLoading: Boolean = false,
    error: String = "",
    endReached: Boolean = false,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    // Debug logging
    LaunchedEffect(movies.size, isLoading, endReached) {
        Log.d(TAG, "$title - Movies count: ${movies.size}, Loading: $isLoading, EndReached: $endReached")
    }

    // Detect when user scrolls to the end
    LaunchedEffect(listState, movies.size) {
        snapshotFlow { listState.layoutInfo }
            .map { layoutInfo ->
                val visibleItems = layoutInfo.visibleItemsInfo
                val totalItems = layoutInfo.totalItemsCount
                if (visibleItems.isNotEmpty() && totalItems > 0) {
                    val lastVisibleItem = visibleItems.last().index
                    val shouldLoadMore = lastVisibleItem >= totalItems - 2 // Load when 2 items before end

                    // Debug logging
                    Log.d(TAG, "$title - LastVisible: $lastVisibleItem, Total: $totalItems, ShouldLoad: $shouldLoadMore")

                    shouldLoadMore
                } else false
            }
            .distinctUntilChanged()
            .collect { isNearEnd ->
                if (isNearEnd && !isLoading && !endReached && error.isEmpty()) {
                    Log.d(TAG, "$title - Triggering load more")
                    onLoadMore()
                } else {
                    Log.d(TAG, "$title - Not loading more - NearEnd: $isNearEnd, Loading: $isLoading, EndReached: $endReached, HasError: ${error.isNotEmpty()}")
                }
            }
    }

    Column(modifier = modifier.padding(top = 16.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )

        LazyRow(
            state = listState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(movies) { movie ->
                MovieItem(
                    movie = movie,
                    onMovieClick = onMovieClick
                )
            }

            // Loading/Error item at the end
            item {
                when {
                    isLoading -> {
                        Box(
                            modifier = Modifier
                                .width(150.dp)
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        }
                    }

                    error.isNotEmpty() -> {
                        Box(
                            modifier = Modifier
                                .width(150.dp)
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Error",
                                    color = Color.Red,
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Spacer(modifier = Modifier.padding(4.dp))
                                Text(
                                    text = "Retry",
                                    color = MaterialTheme.colorScheme.primary,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.clickable {
                                        Log.d(TAG, "$title - Retry clicked")
                                        onRetry()
                                    }
                                )
                            }
                        }
                    }

                    endReached && movies.isNotEmpty() -> {
                        Box(
                            modifier = Modifier
                                .width(150.dp)
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No more movies",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }
                    }
                }
            }
        }
    }
}