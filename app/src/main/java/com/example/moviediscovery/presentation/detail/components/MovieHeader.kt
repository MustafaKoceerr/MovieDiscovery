package com.example.moviediscovery.presentation.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.moviediscovery.presentation.detail.sampleMovieDetailsList
import com.example.moviediscovery.presentation.theme.MovieDiscoveryTheme
import com.example.moviediscovery.util.Constants
import com.example.moviediscovery.util.Constants.BACKDROP_SIZE
import com.example.moviediscovery.util.Constants.IMAGE_BASE_URL

@Composable
fun MovieHeader(
    title: String,
    backdropPath: String?,
    posterPath: String?,
    modifier: Modifier= Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        // Backdrop image
        if (backdropPath != null) {
            AsyncImage(
                model = "${Constants.IMAGE_BASE_URL}${Constants.BACKDROP_SIZE}$backdropPath",
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Gradient overlay for better text visibility
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.7f),
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            )
                        )
                    )
            )
        }

        // Poster image in the bottom-start corner, overlapping
        if (posterPath != null) {
            Box(
                modifier = Modifier
                    .size(width = 100.dp, height = 150.dp)
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp)
                    .offset(y = 75.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                AsyncImage(
                    model = "${Constants.IMAGE_BASE_URL}${Constants.POSTER_SIZE}$posterPath",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Preview
@Composable
fun MovieHeaderPreview() {
    MovieDiscoveryTheme {
        MovieHeader(
            sampleMovieDetailsList[0].title,
            sampleMovieDetailsList[0].backdropPath,
            sampleMovieDetailsList[0].posterPath,
        )
    }
}