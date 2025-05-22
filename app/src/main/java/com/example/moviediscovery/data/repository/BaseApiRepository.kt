package com.example.moviediscovery.data.repository

import com.example.moviediscovery.domain.repository.LanguageRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class BaseApiRepository @Inject constructor(
    private val languageRepository: LanguageRepository
) {
    suspend fun getLanguageCode(): String {
        return languageRepository.getLanguage().first().code
    }
}