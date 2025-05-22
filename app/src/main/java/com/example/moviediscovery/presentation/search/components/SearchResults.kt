package com.example.moviediscovery.presentation.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moviediscovery.presentation.common.LoadingStateItem
import com.example.moviediscovery.presentation.common.MovieItem
import com.example.moviediscovery.presentation.common.pagination.ErrorStateItem
import com.example.moviediscovery.presentation.common.pagination.MoviePaginationState
import com.example.moviediscovery.presentation.common.pagination.PaginationStateHandler
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun SearchResults(
    paginationState: MoviePaginationState,
    onMovieClick: (Int) -> Unit,
    onLoadMore: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val gridState = rememberLazyGridState()

    // Detect when user scrolls to the end
    LaunchedEffect(gridState, paginationState.items.size) {
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
                if (isNearEnd &&
                    !paginationState.isLoading &&
                    !paginationState.endReached &&
                    paginationState.error.isEmpty()) {
                    onLoadMore()
                }
            }
    }

    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(paginationState.items) { movie ->
            MovieItem(
                movie = movie,
                onMovieClick = onMovieClick
            )
        }

        // Pagination state handling - spans 2 columns for grid
        item {
            PaginationStateHandler(
                paginationState = paginationState,
                onRetry = onRetry,
                itemWidth = 200, // Wider for grid layout
                loadingContent = {
                    LoadingStateItem(width = 200)
                },
                errorContent = {
                    ErrorStateItem(
                        onRetry = onRetry,
                        width = 200,
                        errorText = "Error loading more movies"
                    )
                }
            )
        }
    }
}