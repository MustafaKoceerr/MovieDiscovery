package com.example.moviediscovery.presentation.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviediscovery.domain.model.AppLanguage
import com.example.moviediscovery.domain.usecase.GetLanguageUseCase
import com.example.moviediscovery.domain.usecase.SetLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getLanguageUseCase: GetLanguageUseCase,
    private val setLanguageUseCase: SetLanguageUseCase
) : ViewModel() {

    private val _state = mutableStateOf(SettingsState())
    val state: State<SettingsState> = _state

    init {
        processIntent(SettingsIntent.LoadSettings)
    }

    fun processIntent(intent: SettingsIntent) {
        when (intent) {
            is SettingsIntent.LoadSettings -> {
                loadSettings()
            }
            is SettingsIntent.ChangeLanguage -> {
                updateLanguage(intent.language)
            }
        }
    }

    private fun loadSettings() {
        _state.value = _state.value.copy(isLoading = true)
        getLanguageUseCase().onEach { language ->
            _state.value = _state.value.copy(
                selectedLanguage = language,
                isLoading = false,
                error = ""
            )
        }.launchIn(viewModelScope)
    }

    private fun updateLanguage(language: AppLanguage) {
        val currentLanguage = _state.value.selectedLanguage

        viewModelScope.launch {
            try {
                setLanguageUseCase(language)

                // Only trigger restart if language actually changed
                if (currentLanguage != language) {
                    _state.value = _state.value.copy(shouldRestartActivity = true)
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Failed to update language",
                    isLoading = false
                )
            }
        }
    }

    fun onActivityRestarted() {
        _state.value = _state.value.copy(shouldRestartActivity = false)
    }
}