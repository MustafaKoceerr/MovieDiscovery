package com.example.moviediscovery.presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moviediscovery.presentation.common.ErrorView
import com.example.moviediscovery.presentation.common.LoadingView
import com.example.moviediscovery.presentation.search.components.EmptySearchResults
import com.example.moviediscovery.presentation.search.components.InitialSearchState
import com.example.moviediscovery.presentation.search.components.SearchResults
import com.example.moviediscovery.presentation.search.components.SearchTopBar

@Composable
fun SearchScreen(
    onMovieClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state by viewModel.state
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            SearchTopBar(
                query = state.query,
                onQueryChange = { query ->
                    viewModel.processIntent(SearchIntent.QueryChanged(query))
                },
                onSearch = {
                    viewModel.processIntent(SearchIntent.SearchMovies)
                    focusManager.clearFocus()
                },
                onBackClick = onBackClick,
                onClearClick = {
                    viewModel.processIntent(SearchIntent.QueryChanged(""))
                },
                focusRequester = focusRequester
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                // Show loading only for initial search (not for pagination)
                state.isLoading && state.movies.isEmpty() -> {
                    LoadingView()
                }

                // Show error only if no movies are loaded and there's an error
                state.error.isNotEmpty() && state.movies.isEmpty() -> {
                    ErrorView(
                        message = state.error
                    ) {
                        viewModel.processIntent(SearchIntent.Retry)
                    }
                }

                // Show empty results when query exists but no movies found
                state.movies.isEmpty() && state.query.isNotEmpty() && !state.isLoading -> {
                    EmptySearchResults(query = state.query)
                }

                // Show search results with pagination
                state.movies.isNotEmpty() -> {
                    SearchResults(
                        movies = state.movies,
                        onMovieClick = { movieId ->
                            onMovieClick(movieId)
                            viewModel.processIntent(SearchIntent.MovieClicked(movieId))
                        },
                        onLoadMore = {
                            viewModel.processIntent(SearchIntent.LoadNextPage)
                        },
                        onRetry = {
                            viewModel.processIntent(SearchIntent.Retry)
                        },
                        isLoadingMore = state.isLoadingMore,
                        error = if (state.movies.isNotEmpty()) state.error else "",
                        endReached = state.endReached
                    )
                }

                else -> {
                    // Initial state or empty query
                    InitialSearchState()
                }
            }
        }
    }
}