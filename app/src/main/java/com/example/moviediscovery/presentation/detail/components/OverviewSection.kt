package com.example.moviediscovery.presentation.detail.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviediscovery.presentation.detail.sampleMovieDetailsList

@Composable
fun OverviewSection(overview: String) {
    // Overview title
    Text(
        text = "Overview",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
    )

    // overview content
    Text(
        text = overview,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(bottom = 24.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun OverviewSectionPreview() {
    OverviewSection(sampleMovieDetailsList[0].overview)
}