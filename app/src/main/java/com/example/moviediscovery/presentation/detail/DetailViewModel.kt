package com.example.moviediscovery.presentation.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviediscovery.domain.model.Resource
import com.example.moviediscovery.domain.usecase.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(DetailState(isLoading = true))
    val state: State<DetailState> = _state

    // Retrieve the movieId from navigation arguments if available
    private var movieId: Int? = savedStateHandle.get<Int>("movieId")

    init {
        // Only load details automatically if movieId is available from navigation
        movieId?.let {
            loadMovieDetails()
        }
    }

    fun setMovieId(id:Int){
        // Only reload if the ID has changed
        if (movieId != id) {
            movieId = id
            loadMovieDetails()
        }
    }

    fun processIntent(intent: DetailIntent) {
        when (intent) {
            is DetailIntent.LoadMovieDetails -> loadMovieDetails()
        }
    }

    private fun loadMovieDetails() {
        // Ensure we have a movieId before loading
        val id = movieId ?: return

        getMovieDetailsUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = DetailState(
                        movieDetails = result.data,
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    _state.value = DetailState(
                        error = result.message ?: "An unexpected error occured",
                        isLoading = false
                    )
                }

                is Resource.Loading -> {
                    _state.value = DetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}