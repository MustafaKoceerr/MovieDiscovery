package com.example.moviediscovery.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviediscovery.domain.usecase.GetLanguageUseCase
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseViewModel(
    private val getLanguageUseCase: GetLanguageUseCase
) : ViewModel() {

    protected fun observeLanguageChanges(onLanguageChanged: () -> Unit) {
        getLanguageUseCase()
            .distinctUntilChanged()
            .onEach { onLanguageChanged() }
            .launchIn(viewModelScope)
    }
}