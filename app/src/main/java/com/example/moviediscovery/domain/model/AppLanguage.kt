package com.example.moviediscovery.domain.model

enum class AppLanguage(val code: String) {
    ENGLISH("en-US"),
    TURKISH("tr-TR");

    companion object {
        fun fromCode(code: String): AppLanguage {
            return entries.find { language: AppLanguage -> language.code == code } ?: ENGLISH
        }
    }
}