package com.example.moviediscovery.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.moviediscovery.domain.model.Resource
import com.example.moviediscovery.domain.usecase.GetLanguageUseCase
import com.example.moviediscovery.domain.usecase.SearchMoviesUseCase
import com.example.moviediscovery.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
                searchMovies()
            }

            is SearchIntent.LoadNextPage -> {
                loadNextPage()
            }

            is SearchIntent.Retry -> {
                retrySearch()
            }

            is SearchIntent.RefreshSearch -> {
                refreshSearch()
            }

            is SearchIntent.MovieClicked -> {
                // will be handled by navigation
            }
        }
    }

    private fun searchMoviesWithDebounce() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(600) // Debounce for better UX
            searchMovies(page = 1, isRefresh = true)
        }
    }

    private fun searchMovies(page: Int = 1, isRefresh: Boolean = false) {
        val query = _state.value.query
        if (query.isEmpty()) {
            _state.value = _state.value.copy(
                movies = emptyList(),
                currentPage = 1,
                endReached = false
            )
            return
        }

        // Set loading states
        if (page == 1 || isRefresh) {
            _state.value = _state.value.copy(isLoading = true, error = "")
        } else {
            _state.value = _state.value.copy(isLoadingMore = true)
        }

        searchMoviesUseCase(query, page).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val newMovies = result.data ?: emptyList()
                    val allMovies = if (page == 1 || isRefresh) {
                        newMovies
                    } else {
                        _state.value.movies + newMovies
                    }

                    _state.value = _state.value.copy(
                        movies = allMovies,
                        isLoading = false,
                        isLoadingMore = false,
                        error = "",
                        currentPage = page,
                        endReached = newMovies.isEmpty()
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "An unexpected error occurred",
                        isLoading = false,
                        isLoadingMore = false
                    )
                }

                is Resource.Loading -> {
                    if (page == 1 || isRefresh) {
                        _state.value = _state.value.copy(isLoading = true)
                    } else {
                        _state.value = _state.value.copy(isLoadingMore = true)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun loadNextPage() {
        if (!_state.value.endReached && !_state.value.isLoadingMore && !_state.value.isLoading) {
            searchMovies(_state.value.currentPage + 1)
        }
    }

    private fun retrySearch() {
        val currentPage = if (_state.value.movies.isEmpty()) 1 else _state.value.currentPage
        searchMovies(currentPage)
    }

    private fun refreshSearch() {
        _state.value = _state.value.copy(
            movies = emptyList(),
            currentPage = 1,
            endReached = false
        )
        searchMovies(page = 1, isRefresh = true)
    }
}