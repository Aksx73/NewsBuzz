package com.absut.newsapiclient.data.repository.datasource


import com.absut.newsapiclient.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {

    suspend fun saveArticle(article: Article): Long
    suspend fun deleteArticle(article: Article)
    suspend fun deleteAll()
    fun getSavedArticles(): Flow<List<Article>>

}