package com.example.moviediscovery.presentation.common.pagination

import com.example.moviediscovery.domain.model.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class PaginationController<T>(
    private val scope: CoroutineScope,
    private val onStateChange: (PaginationState<T>) -> Unit
) {
    private var currentState = PaginationState<T>()
    private var currentJob: Job? = null

    fun loadPage(
        page: Int = 1,
        isRefresh: Boolean = false,
        dataSource: (Int) -> Flow<Resource<List<T>>>
    ) {
        currentJob?.cancel()

        if (!isRefresh && page == currentState.currentPage && currentState.isLoading) {
            return
        }

        currentState = if (page == 1 || isRefresh) {
            currentState.copy(isLoading = true, error = "")
        } else {
            currentState.copy(isLoading = true)
        }
        onStateChange(currentState)

        currentJob = dataSource(page).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val newItems = result.data ?: emptyList()
                    val allItems = if (page == 1 || isRefresh) {
                        newItems
                    } else {
                        currentState.items + newItems
                    }

                    currentState = currentState.copy(
                        items = allItems,
                        isLoading = false,
                        error = "",
                        currentPage = page,
                        endReached = newItems.isEmpty()
                    )
                    onStateChange(currentState)
                }

                is Resource.Error -> {
                    currentState = currentState.copy(
                        error = result.message ?: "An unexpected error occurred",
                        isLoading = false
                    )
                    onStateChange(currentState)
                }

                is Resource.Loading -> {
                    // Loading state already set above
                }
            }
        }.launchIn(scope)
    }

    fun loadNextPage(dataSource: (Int) -> Flow<Resource<List<T>>>) {
        if (!currentState.endReached && !currentState.isLoading) {
            loadPage(currentState.currentPage + 1, dataSource = dataSource)
        }
    }

    fun retry(dataSource: (Int) -> Flow<Resource<List<T>>>) {
        val pageToRetry = if (currentState.items.isEmpty()) 1 else currentState.currentPage
        loadPage(pageToRetry, dataSource = dataSource)
    }

    fun refresh(dataSource: (Int) -> Flow<Resource<List<T>>>) {
        currentState = PaginationState()
        onStateChange(currentState)
        loadPage(1, isRefresh = true, dataSource = dataSource)
    }

    fun getCurrentState(): PaginationState<T> = currentState

    fun clear() {
        currentJob?.cancel()
        currentState = PaginationState()
        onStateChange(currentState)
    }
}