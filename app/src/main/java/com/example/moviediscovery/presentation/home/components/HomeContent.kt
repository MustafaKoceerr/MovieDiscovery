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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviediscovery.R
import com.example.moviediscovery.presentation.home.HomeIntent
import com.example.moviediscovery.presentation.home.HomeState
import com.example.moviediscovery.presentation.home.sampleMovies
import com.example.moviediscovery.presentation.theme.MovieDiscoveryTheme

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
            if (state.nowPlayingMovies.isNotEmpty() || state.isNowPlayingLoading || state.nowPlayingError.isNotEmpty()) {
                item {
                    PaginatedMovieSection(
                        title = stringResource(R.string.category_now_playing),
                        movies = state.nowPlayingMovies,
                        onMovieClick = onMovieClick,
                        onLoadMore = { onIntent(HomeIntent.LoadNextNowPlayingPage) },
                        onRetry = { onIntent(HomeIntent.RetryNowPlaying) },
                        isLoading = state.isNowPlayingLoading,
                        error = state.nowPlayingError,
                        endReached = state.nowPlayingEndReached
                    )
                }
            }

            if (state.popularMovies.isNotEmpty() || state.isPopularLoading || state.popularError.isNotEmpty()) {
                item {
                    PaginatedMovieSection(
                        title = stringResource(R.string.category_popular),
                        movies = state.popularMovies,
                        onMovieClick = onMovieClick,
                        onLoadMore = { onIntent(HomeIntent.LoadNextPopularPage) },
                        onRetry = { onIntent(HomeIntent.RetryPopular) },
                        isLoading = state.isPopularLoading,
                        error = state.popularError,
                        endReached = state.popularEndReached
                    )
                }
            }

            if (state.topRatedMovies.isNotEmpty() || state.isTopRatedLoading || state.topRatedError.isNotEmpty()) {
                item {
                    PaginatedMovieSection(
                        title = stringResource(R.string.category_top_rated),
                        movies = state.topRatedMovies,
                        onMovieClick = onMovieClick,
                        onLoadMore = { onIntent(HomeIntent.LoadNextTopRatedPage) },
                        onRetry = { onIntent(HomeIntent.RetryTopRated) },
                        isLoading = state.isTopRatedLoading,
                        error = state.topRatedError,
                        endReached = state.topRatedEndReached
                    )
                }
            }

            if (state.upcomingMovies.isNotEmpty() || state.isUpcomingLoading || state.upcomingError.isNotEmpty()) {
                item {
                    PaginatedMovieSection(
                        title = stringResource(R.string.category_upcoming),
                        movies = state.upcomingMovies,
                        onMovieClick = onMovieClick,
                        onLoadMore = { onIntent(HomeIntent.LoadNextUpcomingPage) },
                        onRetry = { onIntent(HomeIntent.RetryUpcoming) },
                        isLoading = state.isUpcomingLoading,
                        error = state.upcomingError,
                        endReached = state.upcomingEndReached
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun HomeContentPaginationPreview() {
    MovieDiscoveryTheme {
        HomeContent(
            state = tempPaginationState,
            onMovieClick = { },
            onSearchClick = { },
            onIntent = { }
        )
    }
}

val tempPaginationState = HomeState(
    nowPlayingMovies = sampleMovies,
    popularMovies = sampleMovies,
    topRatedMovies = sampleMovies,
    upcomingMovies = sampleMovies,
    isNowPlayingLoading = false,
    isPopularLoading = true,
    isTopRatedLoading = false,
    isUpcomingLoading = false,
    nowPlayingError = "",
    popularError = "",
    topRatedError = "",
    upcomingError = "",
    isLoading = false,
    error = ""
)