package com.absut.newsapiclient.di

import com.absut.newsapiclient.domain.repository.NewsRepository
import com.absut.newsapiclient.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetNewsHeadlinesUseCase(newsRepository: NewsRepository): GetNewsHeadlinesUseCase {
        return GetNewsHeadlinesUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideGetNewsHeadlinesByCategoryUseCase(newsRepository: NewsRepository): GetNewsHeadlinesByCategoryUseCase {
        return GetNewsHeadlinesByCategoryUseCase(newsRepository)
    }


    @Singleton
    @Provides
    fun provideSearchedNewsHeadlinesUseCase(newsRepository: NewsRepository): GetSearchedNewsUseCase {
        return GetSearchedNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideSaveNewsUseCase(newsRepository: NewsRepository): SaveNewsUseCase {
        return SaveNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteSavedNewsUseCase(newsRepository: NewsRepository): DeleteSavedNewsUseCase {
        return DeleteSavedNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteAllSavedNewsUseCase(newsRepository: NewsRepository): DeleteAllSavedNewsUseCase {
        return DeleteAllSavedNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideGetSavedNewsUseCase(newsRepository: NewsRepository): GetSavedNewsUseCase {
        return GetSavedNewsUseCase(newsRepository)
    }

}