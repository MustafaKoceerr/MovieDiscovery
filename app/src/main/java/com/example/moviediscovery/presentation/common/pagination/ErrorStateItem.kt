package com.example.moviediscovery.presentation.common.pagination

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviediscovery.R
import com.example.moviediscovery.presentation.theme.MovieDiscoveryTheme

@Composable
fun ErrorStateItem(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    width: Int = 150,
    errorText: String = stringResource(R.string.error_network),
    retryText: String = stringResource(R.string.error_retry)
) {
    Box(
        modifier = modifier
            .width(width.dp)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = errorText,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = retryText,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.clickable { onRetry() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorStateItemPreview() {
    MovieDiscoveryTheme {
        ErrorStateItem(
            onRetry = {}
        )
    }
}