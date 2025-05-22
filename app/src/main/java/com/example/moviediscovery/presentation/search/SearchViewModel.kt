package com.example.moviediscovery.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.moviediscovery.domain.usecase.GetLanguageUseCase
import com.example.moviediscovery.domain.usecase.SearchMoviesUseCase
import com.example.moviediscovery.presentation.base.BaseViewModel
import com.example.moviediscovery.presentation.common.pagination.PaginationController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    getLanguageUseCase: GetLanguageUseCase
) : BaseViewModel(getLanguageUseCase) {

    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    private var searchJob: Job? = null

    // Pagination controller for search results
    private val paginationController = PaginationController(viewModelScope) { paginationState ->
        _state.value = _state.value.copy(paginationState = paginationState)
    }

    init {
        observeLanguageChanges {
            // Re-search with current query when language changes
            if (_state.value.query.isNotEmpty()) {
                refreshSearch()
            }
        }
    }

    fun processIntent(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.QueryChanged -> {
                _state.value = _state.value.copy(query = intent.query)
                searchMoviesWithDebounce()
            }

            is SearchIntent.SearchMovies -> {
                performSearch()
            }

            is SearchIntent.LoadNextPage -> {
                paginationController.loadNextPage { page ->
                    searchMoviesUseCase(_state.value.query, page)
                }
            }

            is SearchIntent.Retry -> {
                paginationController.retry { page ->
                    searchMoviesUseCase(_state.value.query, page)
                }
            }

            is SearchIntent.RefreshSearch -> {
                refreshSearch()
            }

            is SearchIntent.MovieClicked -> {
                // Handled by navigation
            }
        }
    }

    private fun searchMoviesWithDebounce() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(600) // Debounce for better UX
            performSearch()
        }
    }

    private fun performSearch() {
        val query = _state.value.query.trim()
        if (query.isEmpty()) {
            clearSearch()
            return
        }

        _state.value = _state.value.copy(isInitialSearch = true)
        paginationController.refresh { page -> searchMoviesUseCase(query, page) }
        _state.value = _state.value.copy(isInitialSearch = false)
    }

    private fun refreshSearch() {
        val query = _state.value.query.trim()
        if (query.isEmpty()) {
            clearSearch()
            return
        }

        paginationController.refresh { page -> searchMoviesUseCase(query, page) }
    }

    private fun clearSearch() {
        paginationController.clear()
    }

    override fun onCleared() {
        super.onCleared()
        paginationController.clear()
        searchJob?.cancel()
    }
}