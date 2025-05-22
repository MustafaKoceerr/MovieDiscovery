package com.example.moviediscovery.presentation.settings

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.moviediscovery.domain.model.AppLanguage
import com.example.moviediscovery.domain.usecase.GetLanguageUseCase
import com.example.moviediscovery.domain.usecase.SetLanguageUseCase
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
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
class SettingsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var getLanguageUseCase: GetLanguageUseCase
    private lateinit var setLanguageUseCase: SetLanguageUseCase
    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getLanguageUseCase = mockk()
        setLanguageUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should load settings`() = testScope.runTest {
        // Given
        coEvery { getLanguageUseCase() } returns flowOf(AppLanguage.ENGLISH)

        // When
        viewModel = SettingsViewModel(getLanguageUseCase, setLanguageUseCase)
        advanceUntilIdle() // Allow coroutines to complete

        // Then
        assertEquals(AppLanguage.ENGLISH, viewModel.state.value.selectedLanguage)
        assertFalse(viewModel.state.value.isLoading)
        assertEquals("", viewModel.state.value.error)
    }

    @Test
    fun `processIntent LoadSettings should update state with language`() = testScope.runTest {
        // Given
        coEvery { getLanguageUseCase() } returns flowOf(AppLanguage.TURKISH)
        viewModel = SettingsViewModel(getLanguageUseCase, setLanguageUseCase)
        advanceUntilIdle() // Allow coroutines to complete

        // When
        viewModel.processIntent(SettingsIntent.LoadSettings)
        advanceUntilIdle() // Allow coroutines to complete

        // Then
        assertEquals(AppLanguage.TURKISH, viewModel.state.value.selectedLanguage)
        assertFalse(viewModel.state.value.isLoading)
        assertEquals("", viewModel.state.value.error)
    }

    @Test
    fun `processIntent ChangeLanguage to same language should not trigger restart`() = testScope.runTest {
        // Given
        coEvery { getLanguageUseCase() } returns flowOf(AppLanguage.ENGLISH)
        coJustRun { setLanguageUseCase(any()) }
        viewModel = SettingsViewModel(getLanguageUseCase, setLanguageUseCase)
        advanceUntilIdle() // Allow coroutines to complete

        // When
        viewModel.processIntent(SettingsIntent.ChangeLanguage(AppLanguage.ENGLISH))
        advanceUntilIdle() // Allow coroutines to complete

        // Then
        assertFalse(viewModel.state.value.shouldRestartActivity)
        coVerify { setLanguageUseCase(AppLanguage.ENGLISH) }
    }

    @Test
    fun `processIntent ChangeLanguage to different language should trigger restart`() = testScope.runTest {
        // Given
        coEvery { getLanguageUseCase() } returns flowOf(AppLanguage.ENGLISH)
        coJustRun { setLanguageUseCase(any()) }
        viewModel = SettingsViewModel(getLanguageUseCase, setLanguageUseCase)
        advanceUntilIdle() // Allow coroutines to complete

        // When
        viewModel.processIntent(SettingsIntent.ChangeLanguage(AppLanguage.TURKISH))
        advanceUntilIdle() // Allow coroutines to complete

        // Then
        assertTrue(viewModel.state.value.shouldRestartActivity)
        coVerify { setLanguageUseCase(AppLanguage.TURKISH) }
    }

    @Test
    fun `onActivityRestarted should reset shouldRestartActivity flag`() = testScope.runTest {
        // Given
        coEvery { getLanguageUseCase() } returns flowOf(AppLanguage.ENGLISH)
        coJustRun { setLanguageUseCase(any()) }
        viewModel = SettingsViewModel(getLanguageUseCase, setLanguageUseCase)
        advanceUntilIdle() // Allow coroutines to complete

        viewModel.processIntent(SettingsIntent.ChangeLanguage(AppLanguage.TURKISH))
        advanceUntilIdle() // Allow coroutines to complete

        assertTrue(viewModel.state.value.shouldRestartActivity)

        // When
        viewModel.onActivityRestarted()
        advanceUntilIdle() // Allow coroutines to complete

        // Then
        assertFalse(viewModel.state.value.shouldRestartActivity)
    }

    @Test
    fun `processIntent ChangeLanguage should handle errors`() = testScope.runTest {
        // Given
        coEvery { getLanguageUseCase() } returns flowOf(AppLanguage.ENGLISH)
        coEvery { setLanguageUseCase(any()) } throws Exception("Network error")
        viewModel = SettingsViewModel(getLanguageUseCase, setLanguageUseCase)
        advanceUntilIdle() // Allow coroutines to complete

        // When
        viewModel.processIntent(SettingsIntent.ChangeLanguage(AppLanguage.TURKISH))
        advanceUntilIdle() // Allow coroutines to complete

        // Then
        assertEquals("Network error", viewModel.state.value.error)
        assertFalse(viewModel.state.value.shouldRestartActivity)
    }
}