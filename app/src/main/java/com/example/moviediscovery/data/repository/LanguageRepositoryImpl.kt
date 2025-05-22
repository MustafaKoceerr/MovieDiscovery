package com.example.moviediscovery.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.moviediscovery.data.repository.LanguageRepositoryImpl.Companion.LANGUAGE_KEY
import com.example.moviediscovery.domain.model.AppLanguage
import com.example.moviediscovery.domain.repository.LanguageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LanguageRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : LanguageRepository {

    companion object {
        private const val LANGUAGE_KEY = "app_language"

        // Static method for early access (before Hilt injection)
        fun getLanguageEarly(context: android.content.Context): AppLanguage {
            val sharedPreferences = context.getSharedPreferences(
                "movie_discovery_prefs",
                android.content.Context.MODE_PRIVATE
            )
            val languageCode = sharedPreferences.getString(LANGUAGE_KEY, AppLanguage.ENGLISH.code)
            return AppLanguage.fromCode(languageCode ?: AppLanguage.ENGLISH.code)
        }
    }

    private val _languageFlow = MutableStateFlow(getCurrentLanguage())

    override fun getLanguage(): Flow<AppLanguage> {
        return _languageFlow.asStateFlow()
    }

    override suspend fun setLanguage(language: AppLanguage) {
        sharedPreferences.edit()
            .putString(LANGUAGE_KEY, language.code)
            .apply()

        // Update the flow immediately
        _languageFlow.value = language
    }

    override fun getCurrentLanguage(): AppLanguage {
        val languageCode = sharedPreferences.getString(LANGUAGE_KEY, AppLanguage.ENGLISH.code)
        return AppLanguage.fromCode(languageCode ?: AppLanguage.ENGLISH.code)
    }
}