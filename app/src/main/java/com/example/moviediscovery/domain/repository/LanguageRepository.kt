package com.example.moviediscovery.domain.repository

import com.example.moviediscovery.domain.model.AppLanguage
import kotlinx.coroutines.flow.Flow

interface LanguageRepository {
    fun getLanguage(): Flow<AppLanguage>
    suspend fun setLanguage(language: AppLanguage)
}