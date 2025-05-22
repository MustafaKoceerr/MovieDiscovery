package com.example.moviediscovery.data.repository

import com.example.moviediscovery.domain.repository.LanguageRepository
import javax.inject.Inject

class BaseApiRepository @Inject constructor(
    private val languageRepository: LanguageRepository
) {
    // Use synchronous access instead of Flow.first()
     fun getLanguageCode(): String {
        return languageRepository.getCurrentLanguage().code
    }
}