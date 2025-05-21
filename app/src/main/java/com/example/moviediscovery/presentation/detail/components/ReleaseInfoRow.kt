package com.example.moviediscovery.presentation.detail.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviediscovery.presentation.detail.sampleMovieDetailsList
import com.example.moviediscovery.presentation.theme.MovieDiscoveryTheme

@Composable
fun ReleaseInfoRow(
    releaseDate: String,
    runtime: Int?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Release date
        Text(
            text = "Released: $releaseDate",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // runtime
        runtime?.let { time ->
            Text(
                text = "$time min",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ReleaseInfoRowPreview() {
    MovieDiscoveryTheme {
        ReleaseInfoRow(
            releaseDate = sampleMovieDetailsList[0].releaseDate,
            runtime = sampleMovieDetailsList[0].runtime,
        )
    }
}