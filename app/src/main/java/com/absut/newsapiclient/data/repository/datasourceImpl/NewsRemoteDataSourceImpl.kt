package com.absut.newsapiclient.data.repository.datasourceImpl

import com.absut.newsapiclient.data.remote.NewsAPIService
import com.absut.newsapiclient.data.model.APIResponse
import com.absut.newsapiclient.data.repository.datasource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(private val newsAPIService: NewsAPIService) : NewsRemoteDataSource {

    override suspend fun getTopHeadline(country: String, page: Int): Response<APIResponse> {
        return newsAPIService.getTopHeadlinesByCountry(country, page)
    }

    override suspend fun getTopHeadlineByCategory(country: String, category: String, page: Int): Response<APIResponse> {
       return newsAPIService.getTopHeadlinesByCategory(country, category, page)
    }

    override suspend fun getSearchedNews(query: String, page: Int): Response<APIResponse> {
        return newsAPIService.getSearchResults(query, page)
    }

}