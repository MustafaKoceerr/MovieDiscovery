package com.example.moviediscovery.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.moviediscovery.domain.model.Movie
import com.example.moviediscovery.presentation.home.sampleMovies
import com.example.moviediscovery.presentation.theme.MovieDiscoveryTheme
import com.example.moviediscovery.util.Constants.IMAGE_BASE_URL
import com.example.moviediscovery.util.Constants.POSTER_SIZE
import com.example.moviediscovery.util.formatRatingToOneDecimalUp

@Composable
fun MovieItem(
    movie: Movie,
    onMovieClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .clickable { onMovieClick(movie.id) }
    ) {
        Box(
            modifier = Modifier
                .height(225.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            // Image with proper null handling
            AsyncImage(
                model = movie.posterPath?.let { "$IMAGE_BASE_URL$POSTER_SIZE$it" },
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Gradient overlay for better text visibility
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            )
                        )
                    )
            )

            // Title - no need for null check as mapper provides default
            Text(
                text = movie.title,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            )
        }

        // Rating - simplified logic since mapper ensures voteAverage >= 0
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.size(16.dp)
            )

            // Show rating if it's greater than 0
            if (movie.voteAverage > 0) {
                Text(
                    text = movie.voteAverage.toFloat().formatRatingToOneDecimalUp(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 4.dp)
                )
            } else {
                Text(
                    text = "N/A",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieItemPreview() {
    MovieDiscoveryTheme {
        MovieItem(
            sampleMovies[0],
            onMovieClick = { }
        )
    }
}