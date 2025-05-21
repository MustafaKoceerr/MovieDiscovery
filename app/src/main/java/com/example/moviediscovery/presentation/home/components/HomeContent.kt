package com.example.moviediscovery.presentation.home.components

import android.util.Log
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
import com.example.moviediscovery.presentation.home.HomeState
import com.example.moviediscovery.presentation.home.sampleMovies
import com.example.moviediscovery.presentation.theme.MovieDiscoveryTheme

private val TAG: String = "HomeContent"

@ExperimentalMaterial3Api
@Composable
fun HomeContent(
    state: HomeState,
    onMovieClick: (Int) -> Unit,
    onSearchClick: () -> Unit,
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
            if (state.nowPlayingMovies.isNotEmpty()) {
                item {
                    MovieSection(
                        title = stringResource(R.string.category_now_playing),
                        movies = state.nowPlayingMovies,
                        onMovieClick = onMovieClick
                    )
                }
            }

            if (state.popularMovies.isNotEmpty()) {
                item {
                    MovieSection(
                        title = stringResource(R.string.category_popular),
                        movies = state.popularMovies,
                        onMovieClick = onMovieClick
                    )
                }
            }

            if (state.topRatedMovies.isNotEmpty()) {
                item {
                    MovieSection(
                        title = stringResource(R.string.category_top_rated),
                        movies = state.topRatedMovies,
                        onMovieClick = onMovieClick
                    )
                }
            }

            if (state.upcomingMovies.isNotEmpty()) {
                item {
                    MovieSection(
                        title = stringResource(R.string.category_upcoming),
                        movies = state.upcomingMovies,
                        onMovieClick = onMovieClick
                    )
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {
    MovieDiscoveryTheme {
        HomeContent(
            state = tempState,
            onMovieClick = { movieId ->
                Log.d(TAG, "tıklanilan id: $movieId")
            },
            onSearchClick = {}
        )
    }
}


val tempState = HomeState(
    nowPlayingMovies = sampleMovies + sampleMovies + sampleMovies,
    popularMovies = sampleMovies + sampleMovies,
    topRatedMovies = sampleMovies + sampleMovies,
    upcomingMovies = sampleMovies + sampleMovies,
    isLoading = false,
    error = ""
)
