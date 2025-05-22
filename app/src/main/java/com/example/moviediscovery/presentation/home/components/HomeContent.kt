package com.example.moviediscovery.presentation.home.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.moviediscovery.R
import com.example.moviediscovery.presentation.common.pagination.PaginatedMovieSection
import com.example.moviediscovery.presentation.home.HomeIntent
import com.example.moviediscovery.presentation.home.HomeState

@ExperimentalMaterial3Api
@Composable
fun HomeContent(
    state: HomeState,
    onMovieClick: (Int) -> Unit,
    onSearchClick: () -> Unit,
    onIntent: (HomeIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.home_title)) },
                actions = {
                    IconButton(onClick = onSearchClick) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(R.string.search_movies)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // Now Playing Section
            if (state.nowPlayingState.items.isNotEmpty() ||
                state.nowPlayingState.isLoading ||
                state.nowPlayingState.error.isNotEmpty()) {
                item {
                    PaginatedMovieSection(
                        title = stringResource(R.string.category_now_playing),
                        paginationState = state.nowPlayingState,
                        onMovieClick = onMovieClick,
                        onLoadMore = { onIntent(HomeIntent.LoadNextNowPlayingPage) },
                        onRetry = { onIntent(HomeIntent.RetryNowPlaying) }
                    )
                }
            }

            // Popular Section
            if (state.popularState.items.isNotEmpty() ||
                state.popularState.isLoading ||
                state.popularState.error.isNotEmpty()) {
                item {
                    PaginatedMovieSection(
                        title = stringResource(R.string.category_popular),
                        paginationState = state.popularState,
                        onMovieClick = onMovieClick,
                        onLoadMore = { onIntent(HomeIntent.LoadNextPopularPage) },
                        onRetry = { onIntent(HomeIntent.RetryPopular) }
                    )
                }
            }

            // Top Rated Section
            if (state.topRatedState.items.isNotEmpty() ||
                state.topRatedState.isLoading ||
                state.topRatedState.error.isNotEmpty()) {
                item {
                    PaginatedMovieSection(
                        title = stringResource(R.string.category_top_rated),
                        paginationState = state.topRatedState,
                        onMovieClick = onMovieClick,
                        onLoadMore = { onIntent(HomeIntent.LoadNextTopRatedPage) },
                        onRetry = { onIntent(HomeIntent.RetryTopRated) }
                    )
                }
            }

            // Upcoming Section
            if (state.upcomingState.items.isNotEmpty() ||
                state.upcomingState.isLoading ||
                state.upcomingState.error.isNotEmpty()) {
                item {
                    PaginatedMovieSection(
                        title = stringResource(R.string.category_upcoming),
                        paginationState = state.upcomingState,
                        onMovieClick = onMovieClick,
                        onLoadMore = { onIntent(HomeIntent.LoadNextUpcomingPage) },
                        onRetry = { onIntent(HomeIntent.RetryUpcoming) }
                    )
                }
            }
        }
    }
}