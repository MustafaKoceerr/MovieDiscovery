package com.example.moviediscovery.presentation.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviediscovery.R
import com.example.moviediscovery.domain.model.AppLanguage
import com.example.moviediscovery.presentation.theme.MovieDiscoveryTheme

@Composable
fun LanguageOption(
    language: AppLanguage,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    val text = when (language) {
        AppLanguage.ENGLISH -> stringResource(R.string.settings_language_english)
        AppLanguage.TURKISH -> stringResource(R.string.settings_language_turkish)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onSelect() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = { onSelect() }
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LanguageOptionPreview() {
    MovieDiscoveryTheme {
        LanguageOption(
            language = AppLanguage.TURKISH,
            isSelected = true,
            onSelect = {}
        )
    }
}