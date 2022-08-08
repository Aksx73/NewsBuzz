package com.absut.newsapiclient.data.remote

import com.absut.newsapiclient.BuildConfig
import com.absut.newsapiclient.data.model.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface  NewsAPIService {

    @GET("/v2/top-headlines")
    suspend fun getTopHeadlinesByCountry(
        @Query("country") country: String,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): Response<APIResponse>

    @GET("/v2/top-headlines")
    suspend fun getSearchedNewsByCountry(
        @Query("country") country: String,
        @Query("q") searchQuery: String,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): Response<APIResponse>

    @GET("v2/top-headlines")
    suspend fun getTopHeadlinesByCategory(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): Response<APIResponse>

    @GET("v2/everything")
    suspend fun getSearchResults(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): Response<APIResponse>
}