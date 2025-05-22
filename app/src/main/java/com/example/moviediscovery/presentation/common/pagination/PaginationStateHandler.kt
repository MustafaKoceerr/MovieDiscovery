package com.example.moviediscovery.presentation.common.pagination

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.moviediscovery.presentation.common.LoadingStateItem

@Composable
fun <T> PaginationStateHandler(
    paginationState: PaginationState<T>,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    itemWidth: Int = 150,
    loadingContent: @Composable () -> Unit = {
        LoadingStateItem(modifier = modifier, width = itemWidth)
    },
    errorContent: @Composable () -> Unit = {
        ErrorStateItem(
            onRetry = onRetry,
            modifier = modifier,
            width = itemWidth
        )
    },
    endReachedContent: @Composable () -> Unit = {
        EndReachedStateItem(modifier = modifier, width = itemWidth)
    }
) {
    when {
        paginationState.isLoading -> {
            loadingContent()
        }

        paginationState.error.isNotEmpty() -> {
            errorContent()
        }

        paginationState.endReached && paginationState.items.isNotEmpty() -> {
            endReachedContent()
        }
    }
}