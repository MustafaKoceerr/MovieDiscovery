package com.example.moviediscovery

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.moviediscovery.domain.model.AppLanguage
import com.example.moviediscovery.domain.model.Movie
import com.example.moviediscovery.domain.model.Resource
import com.example.moviediscovery.domain.usecase.GetLanguageUseCase
import com.example.moviediscovery.domain.usecase.GetNowPlayingMoviesUseCase
import com.example.moviediscovery.domain.usecase.GetPopularMoviesUseCase
import com.example.moviediscovery.domain.usecase.GetTopRatedMoviesUseCase
import com.example.moviediscovery.domain.usecase.GetUpcomingMoviesUseCase
import com.example.moviediscovery.presentation.home.HomeIntent
import com.example.moviediscovery.presentation.home.HomeViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
    private lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase
    private lateinit var getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase
    private lateinit var getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase
    private lateinit var getLanguageUseCase: GetLanguageUseCase
    private lateinit var viewModel: HomeViewModel

    private val testNowPlayingMovies = listOf(
        Movie(1, "Now Playing 1", "Overview", "/poster1.jpg", "/backdrop1.jpg", "2023-01-01", 8.5, listOf(1, 2), 100.0),
        Movie(2, "Now Playing 2", "Overview", "/poster2.jpg", "/backdrop2.jpg", "2023-01-02", 7.5, listOf(2, 3), 90.0)
    )

    private val testPopularMovies = listOf(
        Movie(3, "Popular 1", "Overview", "/poster3.jpg", "/backdrop3.jpg", "2023-01-03", 9.0, listOf(1, 3), 120.0),
        Movie(4, "Popular 2", "Overview", "/poster4.jpg", "/backdrop4.jpg", "2023-01-04", 8.0, listOf(4, 5), 110.0)
    )

    private val testTopRatedMovies = listOf(
        Movie(5, "Top Rated 1", "Overview", "/poster5.jpg", "/backdrop5.jpg", "2023-01-05", 9.5, listOf(1, 5), 95.0),
        Movie(6, "Top Rated 2", "Overview", "/poster6.jpg", "/backdrop6.jpg", "2023-01-06", 9.2, listOf(2, 6), 85.0)
    )

    private val testUpcomingMovies = listOf(
        Movie(7, "Upcoming 1", "Overview", "/poster7.jpg", "/backdrop7.jpg", "2023-02-01", 0.0, listOf(7, 8), 80.0),
        Movie(8, "Upcoming 2", "Overview", "/poster8.jpg", "/backdrop8.jpg", "2023-02-02", 0.0, listOf(1, 9), 70.0)
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        getNowPlayingMoviesUseCase = mockk()
        getPopularMoviesUseCase = mockk()
        getTopRatedMoviesUseCase = mockk()
        getUpcomingMoviesUseCase = mockk()
        getLanguageUseCase = mockk()

        // Default setup for all use cases
        every { getNowPlayingMoviesUseCase(1) } returns flowOf(Resource.Success(testNowPlayingMovies))
        every { getPopularMoviesUseCase(1) } returns flowOf(Resource.Success(testPopularMovies))
        every { getTopRatedMoviesUseCase(1) } returns flowOf(Resource.Success(testTopRatedMovies))
        every { getUpcomingMoviesUseCase(1) } returns flowOf(Resource.Success(testUpcomingMovies))
        every { getLanguageUseCase() } returns flowOf(AppLanguage.ENGLISH)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init loads all movie categories`() = testScope.runTest {
        // When
        viewModel = HomeViewModel(
            getNowPlayingMoviesUseCase,
            getPopularMoviesUseCase,
            getTopRatedMoviesUseCase,
            getUpcomingMoviesUseCase,
            getLanguageUseCase
        )
        advanceUntilIdle()

        // Then
        with(viewModel.state.value) {
            assertEquals(testNowPlayingMovies, nowPlayingState.items)
            assertEquals(testPopularMovies, popularState.items)
            assertEquals(testTopRatedMovies, topRatedState.items)
            assertEquals(testUpcomingMovies, upcomingState.items)
            assertFalse(isInitialLoading)
            assertEquals("", generalError)
        }

        // Verify all APIs were called
        verify { getNowPlayingMoviesUseCase(1) }
        verify { getPopularMoviesUseCase(1) }
        verify { getTopRatedMoviesUseCase(1) }
        verify { getUpcomingMoviesUseCase(1) }
    }

    @Test
    fun `LoadMovies intent reloads all categories`() = testScope.runTest {
        // Given
        viewModel = HomeViewModel(
            getNowPlayingMoviesUseCase,
            getPopularMoviesUseCase,
            getTopRatedMoviesUseCase,
            getUpcomingMoviesUseCase,
            getLanguageUseCase
        )
        advanceUntilIdle()

        // Clear previous invocations
        io.mockk.clearMocks(
            getNowPlayingMoviesUseCase,
            getPopularMoviesUseCase,
            getTopRatedMoviesUseCase,
            getUpcomingMoviesUseCase
        )

        // When
        viewModel.processIntent(HomeIntent.LoadMovies)
        advanceUntilIdle()

        // Then
        verify { getNowPlayingMoviesUseCase(1) }
        verify { getPopularMoviesUseCase(1) }
        verify { getTopRatedMoviesUseCase(1) }
        verify { getUpcomingMoviesUseCase(1) }
    }

    @Test
    fun `LoadNextNowPlayingPage loads next page`() = testScope.runTest {
        // Given
        val page2Movies = listOf(
            Movie(9, "Now Playing 3", "Overview", "/poster9.jpg", "/backdrop9.jpg", "2023-01-07", 8.0, listOf(1, 2), 85.0)
        )
        every { getNowPlayingMoviesUseCase(2) } returns flowOf(Resource.Success(page2Movies))

        viewModel = HomeViewModel(
            getNowPlayingMoviesUseCase,
            getPopularMoviesUseCase,
            getTopRatedMoviesUseCase,
            getUpcomingMoviesUseCase,
            getLanguageUseCase
        )
        advanceUntilIdle()

        // When
        viewModel.processIntent(HomeIntent.LoadNextNowPlayingPage)
        advanceUntilIdle()

        // Then
        val expectedMovies = testNowPlayingMovies + page2Movies
        assertEquals(expectedMovies, viewModel.state.value.nowPlayingState.items)
        verify { getNowPlayingMoviesUseCase(2) }
    }

    @Test
    fun `RetryNowPlaying retries current page`() = testScope.runTest {
        // Given
        every { getNowPlayingMoviesUseCase(1) } returns
                flowOf(Resource.Error("Network error")) andThen
                flowOf(Resource.Success(testNowPlayingMovies))

        viewModel = HomeViewModel(
            getNowPlayingMoviesUseCase,
            getPopularMoviesUseCase,
            getTopRatedMoviesUseCase,
            getUpcomingMoviesUseCase,
            getLanguageUseCase
        )
        advanceUntilIdle()

        // Initial load should have error
        assertEquals("Network error", viewModel.state.value.nowPlayingState.error)
        assertTrue(viewModel.state.value.nowPlayingState.items.isEmpty())

        // When
        viewModel.processIntent(HomeIntent.RetryNowPlaying)
        advanceUntilIdle()

        // Then
        assertEquals("", viewModel.state.value.nowPlayingState.error)
        assertEquals(testNowPlayingMovies, viewModel.state.value.nowPlayingState.items)
        verify(exactly = 2) { getNowPlayingMoviesUseCase(1) }
    }

    @Test
    fun `RefreshAll refreshes all categories`() = testScope.runTest {
        // Given
        viewModel = HomeViewModel(
            getNowPlayingMoviesUseCase,
            getPopularMoviesUseCase,
            getTopRatedMoviesUseCase,
            getUpcomingMoviesUseCase,
            getLanguageUseCase
        )
        advanceUntilIdle()

        // Clear previous invocations
        io.mockk.clearMocks(
            getNowPlayingMoviesUseCase,
            getPopularMoviesUseCase,
            getTopRatedMoviesUseCase,
            getUpcomingMoviesUseCase
        )

        // When
        viewModel.processIntent(HomeIntent.RefreshAll)
        advanceUntilIdle()

        // Then
        verify { getNowPlayingMoviesUseCase(1) }
        verify { getPopularMoviesUseCase(1) }
        verify { getTopRatedMoviesUseCase(1) }
        verify { getUpcomingMoviesUseCase(1) }
    }

    @Test
    fun `language change triggers refresh`() = testScope.runTest {
        // Given
        val languageFlow = kotlinx.coroutines.flow.MutableStateFlow(AppLanguage.ENGLISH)
        every { getLanguageUseCase() } returns languageFlow

        viewModel = HomeViewModel(
            getNowPlayingMoviesUseCase,
            getPopularMoviesUseCase,
            getTopRatedMoviesUseCase,
            getUpcomingMoviesUseCase,
            getLanguageUseCase
        )
        advanceUntilIdle()

        // Clear previous invocations
        io.mockk.clearMocks(
            getNowPlayingMoviesUseCase,
            getPopularMoviesUseCase,
            getTopRatedMoviesUseCase,
            getUpcomingMoviesUseCase
        )

        // When - language changes
        languageFlow.value = AppLanguage.TURKISH
        advanceUntilIdle()

        // Then - should trigger refresh
        verify { getNowPlayingMoviesUseCase(1) }
        verify { getPopularMoviesUseCase(1) }
        verify { getTopRatedMoviesUseCase(1) }
        verify { getUpcomingMoviesUseCase(1) }
    }
}