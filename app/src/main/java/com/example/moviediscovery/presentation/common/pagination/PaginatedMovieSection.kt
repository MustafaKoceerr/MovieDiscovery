package com.example.moviediscovery.presentation.common.pagination

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moviediscovery.presentation.common.LoadingStateItem
import com.example.moviediscovery.presentation.common.MovieItem
import com.example.moviediscovery.presentation.home.components.SectionTitle

@Composable
fun PaginatedMovieSection(
    title: String,
    paginationState: MoviePaginationState,
    onMovieClick: (Int) -> Unit,
    onLoadMore: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(top = 16.dp)) {
        SectionTitle(title = title)

        PaginationAwareLazyRow(
            items = paginationState.items,
            onLoadMore = onLoadMore,
            shouldLoadMore = {
                !paginationState.isLoading &&
                        !paginationState.endReached &&
                        paginationState.error.isEmpty()
            },
            itemContent = { movie ->
                MovieItem(
                    movie = movie,
                    onMovieClick = onMovieClick
                )
            },
            paginationContent = {
                PaginationStateHandler(
                    paginationState = paginationState,
                    onRetry = onRetry,
                    loadingContent = {
                        LoadingStateItem() // Using the simple loading for pagination
                    }
                )
            }
        )
    }
}