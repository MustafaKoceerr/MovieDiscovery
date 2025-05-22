package com.example.moviediscovery.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moviediscovery.R
import com.example.moviediscovery.presentation.common.ErrorView
import com.example.moviediscovery.presentation.common.LoadingView
import com.example.moviediscovery.presentation.settings.components.LanguageSettings
import com.example.moviediscovery.presentation.theme.MovieDiscoveryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val state by viewModel.state

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.settings_title)) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            when {
                state.isLoading -> {
                    LoadingView()
                }

                state.error.isNotEmpty() -> {
                    ErrorView(message = state.error) {
                        viewModel.processIntent(SettingsIntent.LoadSettings)
                    }
                }

                else -> {
                    LanguageSettings(
                        selectedLanguage = state.selectedLanguage,
                        onLanguageSelected = { language ->
                            viewModel.processIntent(SettingsIntent.ChangeLanguage(language))
                        }
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    MovieDiscoveryTheme {
        SettingsScreen()
    }
}