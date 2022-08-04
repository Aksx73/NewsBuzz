package com.absut.newsapiclient.domain.repository

import com.absut.newsapiclient.data.model.APIResponse
import com.absut.newsapiclient.data.model.Article
import com.absut.newsapiclient.utils.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NewsRepository {

    suspend fun getNewsHeadlines(country: String, page: Int): Resource<APIResponse>
    suspend fun getNewsHeadlinesByCategory(country: String,category:String, page: Int): Resource<APIResponse>
    suspend fun getSearchedNews(searchQuery: String,page: Int): Resource<APIResponse>

    suspend fun saveNews(article: Article):Long
    suspend fun deleteNews(article: Article)
    suspend fun deleteAllNews()
    fun getSavedNews(): Flow<List<Article>>

}