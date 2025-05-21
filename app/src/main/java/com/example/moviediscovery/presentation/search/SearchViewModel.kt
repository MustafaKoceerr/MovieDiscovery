package com.example.moviediscovery.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviediscovery.domain.model.Resource
import com.example.moviediscovery.domain.usecase.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    private var searchJob: Job? = null

    fun processIntent(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.QueryChanged -> {
                _state.value = _state.value.copy(query = intent.query)
                searchMoviesWithDebounce()
            }

            is SearchIntent.SearchMovies -> {
                searchMovies()

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
            searchMovies()
        }
    }

    private fun searchMovies() {
        val query = _state.value.query
        if (query.isEmpty()) {
            _state.value = _state.value.copy(movies = emptyList())
            return
        }

        searchMoviesUseCase(query).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        movies = result.data ?: emptyList(),
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "An unexpected error occurred",
                        isLoading = false
                    )
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}