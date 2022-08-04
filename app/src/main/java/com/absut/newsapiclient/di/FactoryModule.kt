package com.absut.newsapiclient.di

import android.app.Application
import com.absut.newsapiclient.domain.usecase.*
import com.absut.newsapiclient.presentation.viewmodel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class FactoryModule {

    @Singleton
    @Provides
    fun provideNewsViewModelFactory(
        app: Application,
        getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
        getNewsHeadlinesByCategoryUseCase: GetNewsHeadlinesByCategoryUseCase,
        getSearchedNewsUseCase: GetSearchedNewsUseCase,
        getSavedNewsUseCase: GetSavedNewsUseCase,
        saveNewsUseCase: SaveNewsUseCase,
        deleteSavedNewsUseCase: DeleteSavedNewsUseCase,
        deleteAllSavedNewsUseCase: DeleteAllSavedNewsUseCase
    ): NewsViewModelFactory {
        return NewsViewModelFactory(
            app,
            getNewsHeadlinesUseCase,
            getNewsHeadlinesByCategoryUseCase,
            getSearchedNewsUseCase,
            saveNewsUseCase,
            deleteSavedNewsUseCase,
            deleteAllSavedNewsUseCase,
            getSavedNewsUseCase
        )
    }

}