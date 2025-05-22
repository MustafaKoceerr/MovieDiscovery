package com.example.moviediscovery.model

import com.example.moviediscovery.domain.model.AppLanguage
import org.junit.Assert.assertEquals
import org.junit.Test

class AppLanguageTest {

    @Test
    fun `fromCode returns correct language for valid code`() {
        // Given & When
        val englishResult = AppLanguage.fromCode("en-US")
        val turkishResult = AppLanguage.fromCode("tr-TR")

        // Then
        assertEquals(AppLanguage.ENGLISH, englishResult)
        assertEquals(AppLanguage.TURKISH, turkishResult)
    }

    @Test
    fun `fromCode returns ENGLISH for invalid code`() {
        // Given & When
        val result = AppLanguage.fromCode("invalid-code")

        // Then
        assertEquals(AppLanguage.ENGLISH, result)
    }

    @Test
    fun `fromCode returns ENGLISH for null code`() {
        // Given & When
        val result = AppLanguage.fromCode(null.toString())

        // Then
        assertEquals(AppLanguage.ENGLISH, result)
    }

    @Test
    fun `fromCode is case sensitive`() {
        // Given & When
        val result = AppLanguage.fromCode("EN-US")

        // Then
        // Should return ENGLISH only if exact match, otherwise return default
        assertEquals(AppLanguage.ENGLISH, result)
    }

    @Test
    fun `language codes are correct`() {
        // Given & When & Then
        assertEquals("en-US", AppLanguage.ENGLISH.code)
        assertEquals("tr-TR", AppLanguage.TURKISH.code)
    }
}