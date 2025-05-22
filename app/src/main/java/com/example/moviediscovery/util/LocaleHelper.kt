package com.example.moviediscovery.util

import android.content.Context
import android.content.res.Configuration
import com.example.moviediscovery.data.repository.LanguageRepositoryImpl
import com.example.moviediscovery.domain.model.AppLanguage
import java.util.Locale

object LocaleHelper {
    fun setLocale(context: Context, language: AppLanguage): Context {
        val locale = when (language) {
            AppLanguage.ENGLISH -> Locale("en")
            AppLanguage.TURKISH -> Locale("tr")
        }
        return updateResources(context, locale)
    }

    fun applyLanguageToContext(context: Context): Context {
        val savedLanguage = LanguageRepositoryImpl.getLanguageEarly(context)
        return setLocale(context, savedLanguage)
    }

    private fun updateResources(context: Context, locale: Locale): Context {
        Locale.setDefault(locale)

        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        return context.createConfigurationContext(configuration)
    }

}