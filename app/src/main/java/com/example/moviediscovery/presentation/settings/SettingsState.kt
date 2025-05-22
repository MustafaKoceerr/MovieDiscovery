package com.example.moviediscovery.presentation.settings

import com.example.moviediscovery.domain.model.AppLanguage

data class SettingsState(
    val selectedLanguage: AppLanguage = AppLanguage.ENGLISH,
    val isLoading: Boolean = false,
    val error: String = ""
)