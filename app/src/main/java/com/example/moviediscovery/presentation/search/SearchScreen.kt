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
                state.isLoading -> LoadingView()

                state.error.isNotEmpty() && state.movies.isEmpty() -> {
                    ErrorView(
                        message = state.error
                    ) {
                        viewModel.processIntent(SearchIntent.SearchMovies)
                    }
                }

                state.movies.isEmpty() && state.query.isNotEmpty() -> {
                    EmptySearchResults(query = state.query)
                }

                state.movies.isNotEmpty() -> {
                    SearchResults(
                        movies = state.movies,
                        onMovieClick = { movieId ->
                            onMovieClick(movieId)
                            viewModel.processIntent(SearchIntent.MovieClicked(movieId))
                        }
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


