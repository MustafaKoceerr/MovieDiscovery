package com.example.moviediscovery.presentation.detail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.moviediscovery.R
import com.example.moviediscovery.domain.model.MovieDetails
import com.example.moviediscovery.presentation.detail.sampleMovieDetailsList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsContent(
    movieDetails: MovieDetails,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            tint =  MaterialTheme.colorScheme.onBackground,
                            contentDescription = stringResource(R.string.search_back)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            MovieDetailsScrollableContent(movieDetails = movieDetails)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MovieDetailsContentPreview() {
    MovieDetailsContent(
        movieDetails = sampleMovieDetailsList[0],
        onBackClick = {})
}