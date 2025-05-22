package com.example.moviediscovery.domain.usecase

import com.example.moviediscovery.domain.model.AppLanguage
import com.example.moviediscovery.domain.repository.LanguageRepository
import javax.inject.Inject

class SetLanguageUseCase @Inject constructor(
    private val languageRepository: LanguageRepository
) {
    suspend operator fun invoke(language: AppLanguage) {
        languageRepository.setLanguage(language)
    }

}