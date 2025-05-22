package com.example.moviediscovery

import android.content.SharedPreferences
import com.example.moviediscovery.data.repository.LanguageRepositoryImpl
import com.example.moviediscovery.domain.model.AppLanguage
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LanguageRepositoryImplTest {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor
    private lateinit var languageRepository: LanguageRepositoryImpl

    @Before
    fun setUp() {
        sharedPreferences = mockk(relaxed = true)
        sharedPreferencesEditor = mockk(relaxed = true)

        every { sharedPreferences.edit() } returns sharedPreferencesEditor
        every { sharedPreferencesEditor.apply() } returns Unit

        languageRepository = LanguageRepositoryImpl(sharedPreferences)
    }

    @Test
    fun `getCurrentLanguage returns ENGLISH when no language is stored`() {
        // Given
        every { sharedPreferences.getString("app_language", any()) } returns null

        // When
        val result = languageRepository.getCurrentLanguage()

        // Then
        assertEquals(AppLanguage.ENGLISH, result)
    }

    @Test
    fun `getCurrentLanguage returns stored language when available`() {
        // Given
        every { sharedPreferences.getString("app_language", any()) } returns "tr-TR"

        // When
        val result = languageRepository.getCurrentLanguage()

        // Then
        assertEquals(AppLanguage.TURKISH, result)
    }

    @Test
    fun `setLanguage saves language code to SharedPreferences`() = runTest {
        // Given
        val languageSlot = slot<String>()
        every { sharedPreferencesEditor.putString(any(), capture(languageSlot)) } returns sharedPreferencesEditor

        // When
        languageRepository.setLanguage(AppLanguage.TURKISH)

        // Then
        verify { sharedPreferencesEditor.putString("app_language", "tr-TR") }
        assertEquals("tr-TR", languageSlot.captured)
    }

    @Test
    fun `getLanguage emits current language from flow`() = runTest {
        // Given
        every { sharedPreferences.getString("app_language", any()) } returns "en-US"

        // When
        val result = languageRepository.getLanguage().first()

        // Then
        assertEquals(AppLanguage.ENGLISH, result)
    }

    @Test
    fun `setLanguage updates language flow`() = runTest {
        // Given
        every { sharedPreferencesEditor.putString(any(), any()) } returns sharedPreferencesEditor

        // When
        languageRepository.setLanguage(AppLanguage.TURKISH)
        val result = languageRepository.getLanguage().first()

        // Then
        assertEquals(AppLanguage.TURKISH, result)
    }

    @Test
    fun `AppLanguage fromCode handles invalid codes`() {
        // Given
        val invalidCode = "invalid-code"

        // When
        val result = AppLanguage.fromCode(invalidCode)

        // Then
        assertEquals(AppLanguage.ENGLISH, result)
    }
}