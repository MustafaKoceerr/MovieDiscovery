package com.example.moviediscovery.presentation.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviediscovery.domain.model.Genre
import com.example.moviediscovery.presentation.theme.MovieDiscoveryTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenreChips(
    genres: List<Genre>,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        genres.forEach { genre ->
            SuggestionChip(
                onClick = { /* no action needed*/ },
                label = { Text(text = genre.name) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GenreChipsPreview(){
    MovieDiscoveryTheme {
        GenreChips(genreSamples)
    }
}

val genreSamples = listOf(
    Genre(id = 28, name = "Action"),
    Genre(id = 12, name = "Adventure"),
    Genre(id = 16, name = "Animation"),
    Genre(id = 35, name = "Comedy"),
    Genre(id = 18, name = "Drama")
)