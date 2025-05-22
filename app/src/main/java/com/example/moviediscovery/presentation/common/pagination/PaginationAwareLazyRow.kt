package com.example.moviediscovery.presentation.common.pagination

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun <T> PaginationAwareLazyRow(
    items: List<T>,
    onLoadMore: () -> Unit,
    shouldLoadMore: () -> Boolean,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(16.dp),
    loadThreshold: Int = 2,
    itemContent: @Composable (T) -> Unit,
    paginationContent: @Composable () -> Unit = {}
) {
    // Handle pagination detection
    LaunchedEffect(state, items.size) {
        snapshotFlow { state.layoutInfo }
            .map { layoutInfo ->
                val visibleItems = layoutInfo.visibleItemsInfo
                val totalItems = layoutInfo.totalItemsCount
                if (visibleItems.isNotEmpty() && totalItems > 0) {
                    val lastVisibleItem = visibleItems.last().index
                    lastVisibleItem >= totalItems - loadThreshold
                } else false
            }
            .distinctUntilChanged()
            .collect { isNearEnd ->
                if (isNearEnd && shouldLoadMore()) {
                    onLoadMore()
                }
            }
    }

    LazyRow(
        state = state,
        contentPadding = contentPadding,
        horizontalArrangement = horizontalArrangement,
        modifier = modifier
    ) {
        items(items) { item ->
            itemContent(item)
        }

        item {
            paginationContent()
        }
    }
}