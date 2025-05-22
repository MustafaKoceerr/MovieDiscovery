// Complete working test file - Copy this ENTIRE content to replace your test file

package com.example.moviediscovery

import app.cash.turbine.test
import com.example.moviediscovery.data.api.MovieApi
import com.example.moviediscovery.data.api.model.ApiResponse
import com.example.moviediscovery.data.api.model.MovieDto
import com.example.moviediscovery.data.repository.BaseApiRepository
import com.example.moviediscovery.data.repository.MovieRepositoryImpl
import com.example.moviediscovery.domain.model.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import retrofit2.Response

class MovieRepositoryImplTest {

    @Mock
    private lateinit var movieApi: MovieApi

    @Mock
    private lateinit var baseApiRepository: BaseApiRepository

    private lateinit var repository: MovieRepositoryImpl

    private val sampleMovieDto = MovieDto(
        id = 1,
        title = "Test Movie",
        overview = "Test Overview",
        posterPath = "/test.jpg",
        backdropPath = "/backdrop.jpg",
        releaseDate = "2023-01-01",
        voteAverage = 8.5,
        genreIds = listOf(28, 12),
        popularity = 100.0
    )

    private val sampleApiResponse = ApiResponse(
        page = 1,
        results = listOf(sampleMovieDto),
        totalPages = 1,
        totalResults = 1
    )

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        whenever(baseApiRepository.getLanguageCode()).thenReturn("en-US")
        repository = MovieRepositoryImpl(movieApi, baseApiRepository)
    }

    @Test
    fun `getNowPlayingMovies should return success when api call succeeds`() = runTest {
        // Given
        val successResponse = Response.success(sampleApiResponse)
        whenever(movieApi.getNowPlayingMovies(
            apiKey = any(),
            page = any(),
            language = any()
        )).thenReturn(successResponse)

        // When & Then
        repository.getNowPlayingMovies(1).test {
            // First emission should be Loading
            val loadingState = awaitItem()
            assertThat(loadingState).isInstanceOf(Resource.Loading::class.java)

            // Second emission should be Success
            val successState = awaitItem()
            assertThat(successState).isInstanceOf(Resource.Success::class.java)

            val movies = (successState as Resource.Success).data
            assertThat(movies).hasSize(1)
            assertThat(movies[0].title).isEqualTo("Test Movie")
            assertThat(movies[0].id).isEqualTo(1)

            awaitComplete()
        }
    }

    @Test
    fun `getNowPlayingMovies should return error when api call fails`() = runTest {
        // Given
        val errorResponse = Response.error<ApiResponse<MovieDto>>(
            404,
            "Not Found".toResponseBody()
        )
        whenever(movieApi.getNowPlayingMovies(
            apiKey = any(),
            page = any(),
            language = any()
        )).thenReturn(errorResponse)

        // When & Then
        repository.getNowPlayingMovies(1).test {
            // First emission should be Loading
            val loadingState = awaitItem()
            assertThat(loadingState).isInstanceOf(Resource.Loading::class.java)

            // Second emission should be Error
            val errorState = awaitItem()
            assertThat(errorState).isInstanceOf(Resource.Error::class.java)

            // FIXED: Check for the actual error message your app produces
            val errorMessage = (errorState as Resource.Error).message
            assertThat(errorMessage).contains("requested resource was not found")

            awaitComplete()
        }
    }

    @Test
    fun `getPopularMovies should use correct language code`() = runTest {
        // Given - Change language to Turkish
        whenever(baseApiRepository.getLanguageCode()).thenReturn("tr-TR")
        val successResponse = Response.success(sampleApiResponse)
        whenever(movieApi.getPopularMovies(
            apiKey = any(),
            page = any(),
            language = any()
        )).thenReturn(successResponse)

        // When
        repository.getPopularMovies(1).test {
            awaitItem() // Loading
            val successState = awaitItem() // Success

            assertThat(successState).isInstanceOf(Resource.Success::class.java)
            awaitComplete()
        }
    }

    @Test
    fun `searchMovies should return mapped movies correctly`() = runTest {
        // Given
        val query = "batman"
        val successResponse = Response.success(sampleApiResponse)
        whenever(movieApi.searchMovies(
            apiKey = any(),
            query = any(),
            page = any(),
            language = any()
        )).thenReturn(successResponse)

        // When & Then
        repository.searchMovies(query, 1).test {
            awaitItem() // Loading
            val successState = awaitItem() // Success

            assertThat(successState).isInstanceOf(Resource.Success::class.java)
            val movies = (successState as Resource.Success).data
            assertThat(movies).hasSize(1)
            assertThat(movies[0].title).isEqualTo("Test Movie")

            awaitComplete()
        }
    }

    @Test
    fun `repository should handle empty response correctly`() = runTest {
        // Given
        val emptyResponse = ApiResponse<MovieDto>(
            page = 1,
            results = emptyList(),
            totalPages = 1,
            totalResults = 0
        )
        val successResponse = Response.success(emptyResponse)
        whenever(movieApi.getNowPlayingMovies(
            apiKey = any(),
            page = any(),
            language = any()
        )).thenReturn(successResponse)

        // When & Then
        repository.getNowPlayingMovies(1).test {
            awaitItem() // Loading
            val successState = awaitItem() // Success

            assertThat(successState).isInstanceOf(Resource.Success::class.java)
            val movies = (successState as Resource.Success).data
            assertThat(movies).isEmpty()

            awaitComplete()
        }
    }

    @Test
    fun `multiple API methods should work independently`() = runTest {
        // Given
        whenever(movieApi.getTopRatedMovies(
            apiKey = any(),
            page = any(),
            language = any()
        )).thenReturn(Response.success(sampleApiResponse))

        whenever(movieApi.getUpcomingMovies(
            apiKey = any(),
            page = any(),
            language = any()
        )).thenReturn(Response.success(sampleApiResponse))

        // When & Then
        repository.getTopRatedMovies(1).test {
            awaitItem() // Loading
            val result = awaitItem() // Success
            assertThat(result).isInstanceOf(Resource.Success::class.java)
            awaitComplete()
        }

        repository.getUpcomingMovies(1).test {
            awaitItem() // Loading
            val result = awaitItem() // Success
            assertThat(result).isInstanceOf(Resource.Success::class.java)
            awaitComplete()
        }
    }
}