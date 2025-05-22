package com.example.moviediscovery.presentation.settings

import com.example.moviediscovery.domain.model.AppLanguage

sealed class SettingsIntent() {
    object LoadSettings : SettingsIntent()
    data class ChangeLanguage(val language: AppLanguage) : SettingsIntent()
}