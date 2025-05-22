package com.example.moviediscovery.data.di

import com.example.moviediscovery.data.repository.LanguageRepositoryImpl
import com.example.moviediscovery.data.repository.MovieRepositoryImpl
import com.example.moviediscovery.domain.repository.LanguageRepository
import com.example.moviediscovery.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    @Singleton
    abstract fun bindLanguageRepository(languageRepositoryImpl: LanguageRepositoryImpl): LanguageRepository
}