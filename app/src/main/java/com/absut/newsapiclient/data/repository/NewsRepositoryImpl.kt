package com.absut.newsapiclient.data.repository

import com.absut.newsapiclient.data.model.APIResponse
import com.absut.newsapiclient.data.model.Article
import com.absut.newsapiclient.data.repository.datasource.NewsLocalDataSource
import com.absut.newsapiclient.data.repository.datasource.NewsRemoteDataSource
import com.absut.newsapiclient.domain.repository.NewsRepository
import com.absut.newsapiclient.utils.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: NewsLocalDataSource
) : NewsRepository {

    override suspend fun getNewsHeadlines(country: String, page: Int): Resource<APIResponse> {
        return responseToResource(newsRemoteDataSource.getTopHeadline(country, page))
    }

    override suspend fun getNewsHeadlinesByCategory(country: String, category: String, page: Int): Resource<APIResponse> {
        return responseToResource(newsRemoteDataSource.getTopHeadlineByCategory(country, category, page))
    }

    override suspend fun getSearchedNews(searchQuery: String, page: Int): Resource<APIResponse> {
        return responseToResource(newsRemoteDataSource.getSearchedNews(searchQuery, page))
    }

    override suspend fun saveNews(article: Article): Long {
        return newsLocalDataSource.saveArticle(article)
    }

    override suspend fun deleteNews(article: Article) {
        return newsLocalDataSource.deleteArticle(article)
    }

    override suspend fun deleteAllNews() {
        return newsLocalDataSource.deleteAll()
    }

    override fun getSavedNews(): Flow<List<Article>> {
        return newsLocalDataSource.getSavedArticles()
    }


    /**
     * Converts Retrofit response object to Resource of response to manage state
     * **/
    /* fun responseToResource(response: Response<APIResponse>): Resource<APIResponse> {
         if (response.isSuccessful) {
             response.body()?.let {
                 return Resource.Success(it)
             }
         }
         return Resource.Error(response.message())
     }*/

    /**
     * Converts Retrofit response object to Resource of response to manage state
     * **/
    fun responseToResource(response: Response<APIResponse>): Resource<APIResponse> {
        return try {
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }

    }


}