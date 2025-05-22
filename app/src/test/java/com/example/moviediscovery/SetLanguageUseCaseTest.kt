package com.example.moviediscovery

import com.example.moviediscovery.domain.model.AppLanguage
import com.example.moviediscovery.domain.repository.LanguageRepository
import com.example.moviediscovery.domain.usecase.SetLanguageUseCase
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SetLanguageUseCaseTest {

    private lateinit var languageRepository: LanguageRepository
    private lateinit var setLanguageUseCase: SetLanguageUseCase

    @Before
    fun setUp() {
        languageRepository = mockk()
        setLanguageUseCase = SetLanguageUseCase(languageRepository)
    }

    @Test
    fun `invoke calls repository setLanguage with given language`() = runTest {
        // Given
        coJustRun { languageRepository.setLanguage(any()) }

        // When
        setLanguageUseCase(AppLanguage.TURKISH)

        // Then
        coVerify { languageRepository.setLanguage(AppLanguage.TURKISH) }
    }

    @Test
    fun `invoke calls repository setLanguage with English language`() = runTest {
        // Given
        coJustRun { languageRepository.setLanguage(any()) }

        // When
        setLanguageUseCase(AppLanguage.ENGLISH)

        // Then
        coVerify { languageRepository.setLanguage(AppLanguage.ENGLISH) }
    }

    @Test
    fun `invoke handles all AppLanguage enum values`() = runTest {
        // Given
        coJustRun { languageRepository.setLanguage(any()) }

        // When & Then
        AppLanguage.entries.forEach { language ->
            setLanguageUseCase(language)
            coVerify { languageRepository.setLanguage(language) }
        }
    }
}