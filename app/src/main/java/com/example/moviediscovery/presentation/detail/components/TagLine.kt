package com.example.moviediscovery.presentation.detail.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviediscovery.presentation.detail.sampleMovieDetailsList
import com.example.moviediscovery.presentation.theme.MovieDiscoveryTheme

@Composable
fun TagLine(tagline: String) {
    Text(
        text = tagline,
        style = MaterialTheme.typography.bodyLarge,
        fontStyle = FontStyle.Italic,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
        modifier = Modifier.padding(vertical = 16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun TagLinePreview() {
    MovieDiscoveryTheme {
        TagLine(sampleMovieDetailsList[0].tagline!!)
    }
}