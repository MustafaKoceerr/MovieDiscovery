package com.example.moviediscovery.data.di

import com.example.moviediscovery.domain.repository.LanguageRepository
import com.example.moviediscovery.domain.usecase.GetLanguageUseCase
import com.example.moviediscovery.domain.usecase.SetLanguageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreUseCaseModule {

    @Provides
    @Singleton
    fun provideGetLanguageUseCase(repository: LanguageRepository): GetLanguageUseCase {
        return GetLanguageUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSetLanguageUseCase(repository: LanguageRepository): SetLanguageUseCase {
        return SetLanguageUseCase(repository)
    }

}