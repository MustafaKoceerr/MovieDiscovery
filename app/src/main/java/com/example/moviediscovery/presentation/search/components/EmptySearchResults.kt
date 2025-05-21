package com.example.moviediscovery.presentation.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviediscovery.presentation.theme.MovieDiscoveryTheme

@Composable
fun EmptySearchResults(query: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "No movies found for \"$query\"",
            style = MaterialTheme.typography.bodyLarge
        )
    }

}

@Preview(showBackground = true)
@Composable
fun EmptySearchResults(){
    MovieDiscoveryTheme {
         EmptySearchResults(query = "Hello all")
    }
}