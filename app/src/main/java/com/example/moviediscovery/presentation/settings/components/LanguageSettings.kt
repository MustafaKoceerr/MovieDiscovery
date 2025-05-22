package com.example.moviediscovery.presentation.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviediscovery.R
import com.example.moviediscovery.domain.model.AppLanguage
import com.example.moviediscovery.presentation.theme.MovieDiscoveryTheme

@Composable
fun LanguageSettings(
    selectedLanguage: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.settings_language),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LanguageOption(
                language = AppLanguage.ENGLISH,
                isSelected = selectedLanguage == AppLanguage.ENGLISH,
                onSelect = { onLanguageSelected(AppLanguage.ENGLISH) }
            )

            LanguageOption(
                language = AppLanguage.TURKISH,
                isSelected = selectedLanguage == AppLanguage.TURKISH,
                onSelect = { onLanguageSelected(AppLanguage.TURKISH) }
            )
        }
    }

}

@Preview
@Composable
fun LanguageSettingsPreview(){
    MovieDiscoveryTheme {
        LanguageSettings(
            selectedLanguage = AppLanguage.TURKISH,
            onLanguageSelected = {},
        )
    }
}