package com.absut.newsapiclient.domain.usecase

import com.absut.newsapiclient.domain.repository.NewsRepository

class DeleteAllSavedNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute() = newsRepository.deleteAllNews()

}