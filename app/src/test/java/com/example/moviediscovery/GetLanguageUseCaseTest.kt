package com.example.moviediscovery

import com.example.moviediscovery.domain.model.AppLanguage
import com.example.moviediscovery.domain.repository.LanguageRepository
import com.example.moviediscovery.domain.usecase.GetLanguageUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetLanguageUseCaseTest {

    private lateinit var languageRepository: LanguageRepository
    private lateinit var getLanguageUseCase: GetLanguageUseCase

    @Before
    fun setUp() {
        languageRepository = mockk()
        getLanguageUseCase = GetLanguageUseCase(languageRepository)
    }

    @Test
    fun `invoke returns language from repository`() = runTest {
        // Given
        every { languageRepository.getLanguage() } returns flowOf(AppLanguage.TURKISH)

        // When
        val result = getLanguageUseCase().first()

        // Then
        assertEquals(AppLanguage.TURKISH, result)
        verify { languageRepository.getLanguage() }
    }

    @Test
    fun `invoke returns English as default when no preference set`() = runTest {
        // Given
        every { languageRepository.getLanguage() } returns flowOf(AppLanguage.ENGLISH)

        // When
        val result = getLanguageUseCase().first()

        // Then
        assertEquals(AppLanguage.ENGLISH, result)
        verify { languageRepository.getLanguage() }
    }
}