package com.absut.newsapiclient.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.absut.newsapiclient.domain.usecase.*

@Suppress("UNCHECKED_CAST")
class NewsViewModelFactory(
    private val app: Application,
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    private val getNewsHeadlinesByCategoryUseCase: GetNewsHeadlinesByCategoryUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase,
    private val deleteAllSavedNewsUseCase: DeleteAllSavedNewsUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(
            app,
            getNewsHeadlinesUseCase,
            getNewsHeadlinesByCategoryUseCase,
            getSearchedNewsUseCase,
            saveNewsUseCase,
            deleteSavedNewsUseCase,
            deleteAllSavedNewsUseCase,
            getSavedNewsUseCase
        ) as T
    }
}