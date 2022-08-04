package com.absut.newsapiclient.data.repository.datasource

import com.absut.newsapiclient.data.model.APIResponse
import retrofit2.Response

interface NewsRemoteDataSource {

    suspend fun getTopHeadline( country: String, page: Int): Response<APIResponse>
    suspend fun getTopHeadlineByCategory( country: String,category:String, page: Int): Response<APIResponse>
    suspend fun getSearchedNews(query:String, page: Int): Response<APIResponse>
}