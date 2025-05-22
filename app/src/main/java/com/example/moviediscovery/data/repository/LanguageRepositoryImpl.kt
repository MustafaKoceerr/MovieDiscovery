package com.example.moviediscovery.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.moviediscovery.domain.model.AppLanguage
import com.example.moviediscovery.domain.repository.LanguageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LanguageRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : LanguageRepository {

    companion object {
        private val LANGUAGE_KEY = stringPreferencesKey("app_language")
    }

    override fun getLanguage(): Flow<AppLanguage> {
        return dataStore.data.map { preferences ->
            val languageCode = preferences[LANGUAGE_KEY] ?: AppLanguage.ENGLISH.code
            AppLanguage.fromCode(languageCode)
        }
    }

    override suspend fun setLanguage(language: AppLanguage) {
        dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language.code
        }
    }
}