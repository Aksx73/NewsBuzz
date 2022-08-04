package com.absut.newsapiclient.data.repository.datasourceImpl

import com.absut.newsapiclient.data.local.dao.ArticleDao
import com.absut.newsapiclient.data.model.Article
import com.absut.newsapiclient.data.repository.datasource.NewsLocalDataSource
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl(private val articleDao: ArticleDao) : NewsLocalDataSource {
    override suspend fun saveArticle(article: Article): Long {
        return articleDao.insert(article)
    }

    override suspend fun deleteArticle(article: Article) {
        return articleDao.delete(article)
    }

    override suspend fun deleteAll() {
        return articleDao.deleteAll()
    }

    override fun getSavedArticles(): Flow<List<Article>> {
        return articleDao.getArticles()
    }
}