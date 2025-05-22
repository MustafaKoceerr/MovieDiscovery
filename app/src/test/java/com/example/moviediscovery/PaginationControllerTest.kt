package com.example.moviediscovery

import com.example.moviediscovery.domain.model.Resource
import com.example.moviediscovery.presentation.common.pagination.PaginationController
import com.example.moviediscovery.presentation.common.pagination.PaginationState
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PaginationControllerTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var paginationController: PaginationController<String>
    private lateinit var stateChangeCallback: (PaginationState<String>) -> Unit
    private var latestState: PaginationState<String>? = null

    @Before
    fun setUp() {
        stateChangeCallback = mockk(relaxed = true)
        val stateSlot = slot<PaginationState<String>>()
        every { stateChangeCallback(capture(stateSlot)) } answers {
            latestState = stateSlot.captured
        }

        paginationController = PaginationController(testScope, stateChangeCallback)
    }

    @Test
    fun `loadPage should update state with success data`() = testScope.runTest {
        // Given
        val testData = listOf("Item1", "Item2")
        val dataSource: (Int) -> kotlinx.coroutines.flow.Flow<Resource<List<String>>> = { page ->
            flow { emit(Resource.Success(testData)) }
        }

        // When
        paginationController.loadPage(1, dataSource = dataSource)
        advanceUntilIdle()

        // Then
        verify { stateChangeCallback(any()) }
        assertEquals(testData, latestState?.items)
        assertEquals(1, latestState?.currentPage)
        assertFalse(latestState?.isLoading ?: true)
        assertEquals("", latestState?.error)
        assertFalse(latestState?.endReached ?: true)
    }

    @Test
    fun `loadPage should handle error state`() = testScope.runTest {
        // Given
        val errorMsg = "Network error"
        val dataSource: (Int) -> kotlinx.coroutines.flow.Flow<Resource<List<String>>> = { page ->
            flow { emit(Resource.Error(errorMsg)) }
        }

        // When
        paginationController.loadPage(1, dataSource = dataSource)
        advanceUntilIdle()

        // Then
        verify { stateChangeCallback(any()) }
        assertTrue(latestState?.items?.isEmpty() ?: false)
        assertEquals(errorMsg, latestState?.error)
        assertFalse(latestState?.isLoading ?: true)
    }

    @Test
    fun `loadPage with page 1 should clear previous items`() = testScope.runTest {
        // Given - initial load with some data
        val initialData = listOf("Item1", "Item2")
        val initialDataSource: (Int) -> kotlinx.coroutines.flow.Flow<Resource<List<String>>> = { page ->
            flow { emit(Resource.Success(initialData)) }
        }
        paginationController.loadPage(1, dataSource = initialDataSource)
        advanceUntilIdle()

        // When - reload page 1 with different data
        val newData = listOf("New1", "New2")
        val newDataSource: (Int) -> kotlinx.coroutines.flow.Flow<Resource<List<String>>> = { page ->
            flow { emit(Resource.Success(newData)) }
        }
        paginationController.loadPage(1, dataSource = newDataSource)
        advanceUntilIdle()

        // Then - should replace previous items
        assertEquals(newData, latestState?.items)
    }

    @Test
    fun `loadNextPage should load next page and append items`() = testScope.runTest {
        // Given - initial load with page 1
        val page1Data = listOf("Item1", "Item2")
        val page2Data = listOf("Item3", "Item4")

        val dataSourceFactory: (Int) -> kotlinx.coroutines.flow.Flow<Resource<List<String>>> = { page ->
            flow {
                emit(
                    if (page == 1) Resource.Success(page1Data)
                    else Resource.Success(page2Data)
                )
            }
        }

        paginationController.loadPage(1, dataSource = dataSourceFactory)
        advanceUntilIdle()
        assertEquals(page1Data, latestState?.items)

        // When - load next page
        paginationController.loadNextPage(dataSourceFactory)
        advanceUntilIdle()

        // Then - should append items
        assertEquals(page1Data + page2Data, latestState?.items)
        assertEquals(2, latestState?.currentPage)
    }


    @Test
    fun `loadNextPage should not load if endReached is true`() = testScope.runTest {
        // Given - empty result indicating end reached
        val dataSource: (Int) -> kotlinx.coroutines.flow.Flow<Resource<List<String>>> = { page ->
            flow { emit(Resource.Success(emptyList())) }
        }

        // Load first page (empty result)
        paginationController.loadPage(1, dataSource = dataSource)
        advanceUntilIdle()
        assertTrue(latestState?.endReached ?: false)

        // When - try to load next page
        paginationController.loadNextPage(dataSource)
        advanceUntilIdle()

        // Then - page should not change from 1
        assertEquals(1, latestState?.currentPage)
    }

    @Test
    fun `refresh should reset state and load first page`() = testScope.runTest {
        // Given - initial data
        val initialData = listOf("Item1", "Item2")
        val refreshedData = listOf("New1", "New2")

        var isFirstCall = true
        val dataSourceFactory: (Int) -> kotlinx.coroutines.flow.Flow<Resource<List<String>>> = { page ->
            flow {
                emit(
                    if (isFirstCall) Resource.Success(initialData)
                    else Resource.Success(refreshedData)
                )
            }
        }

        paginationController.loadPage(1, dataSource = dataSourceFactory)
        advanceUntilIdle()
        assertEquals(initialData, latestState?.items)

        // When
        isFirstCall = false
        paginationController.refresh(dataSourceFactory)
        advanceUntilIdle()

        // Then
        assertEquals(refreshedData, latestState?.items)
        assertEquals(1, latestState?.currentPage)
        assertFalse(latestState?.isLoading ?: true)
    }

    @Test
    fun `retry should reload current page`() = testScope.runTest {
        // Given - failed initial load
        val errorMsg = "Network error"
        val testData = listOf("Item1", "Item2")

        var shouldFail = true
        val dataSourceFactory: (Int) -> kotlinx.coroutines.flow.Flow<Resource<List<String>>> = { page ->
            flow {
                if (shouldFail) {
                    emit(Resource.Error(errorMsg))
                } else {
                    emit(Resource.Success(testData))
                }
            }
        }

        paginationController.loadPage(1, dataSource = dataSourceFactory)
        advanceUntilIdle()
        assertEquals(errorMsg, latestState?.error)

        // When - retry with success
        shouldFail = false
        paginationController.retry(dataSourceFactory)
        advanceUntilIdle()

        // Then
        assertEquals(testData, latestState?.items)
        assertEquals("", latestState?.error)
    }

    @Test
    fun `clear should reset the state`() = testScope.runTest {
        // Given - initial load with data
        val testData = listOf("Item1", "Item2")
        val dataSource: (Int) -> kotlinx.coroutines.flow.Flow<Resource<List<String>>> = { page ->
            flow { emit(Resource.Success(testData)) }
        }

        paginationController.loadPage(1, dataSource = dataSource)
        advanceUntilIdle()
        assertEquals(testData, latestState?.items)

        // When
        paginationController.clear()

        // Then
        assertTrue(latestState?.items?.isEmpty() ?: false)
        assertEquals(1, latestState?.currentPage)
        assertFalse(latestState?.isLoading ?: true)
        assertEquals("", latestState?.error)
    }
}