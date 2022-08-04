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

    /*operator fun invoke(country: String, page: Int): Flow<Resource<List<Article>>> = flow {
        try {
            emit(Resource.Loading<List<Article>>())
            val response = newsRepository.getNewsHeadlines(country, page)
            if (response.isSuccessful) {
                response.body()?.let {
                    val articles = it.articles
                    emit(Resource.Success<List<Article>>(articles))
                }
            }
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<Article>>(
                    e.localizedMessage ?: "An unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<List<Article>>("Couldn't reach server. Check your internet connection."))
        }
    }*/
}