package com.absut.newsapiclient.domain.usecase

import com.absut.newsapiclient.data.model.APIResponse
import com.absut.newsapiclient.data.model.Article
import com.absut.newsapiclient.domain.repository.NewsRepository
import com.absut.newsapiclient.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetNewsHeadlinesUseCase(private val newsRepository: NewsRepository) {

     suspend fun execute(country: String, page: Int): Resource<APIResponse> {
         return newsRepository.getNewsHeadlines(country,page)
     }
}