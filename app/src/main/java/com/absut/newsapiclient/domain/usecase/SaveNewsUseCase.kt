package com.absut.newsapiclient.domain.usecase

import com.absut.newsapiclient.data.model.Article
import com.absut.newsapiclient.domain.repository.NewsRepository

class SaveNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(article: Article): Long = newsRepository.saveNews(article)

}