package com.absut.newsapiclient.domain.usecase

import com.absut.newsapiclient.data.model.APIResponse
import com.absut.newsapiclient.domain.repository.NewsRepository
import com.absut.newsapiclient.utils.Resource

class GetNewsHeadlinesByCategoryUseCase(private val newsRepository: NewsRepository) {

   suspend fun execute(country:String,category:String,page:Int):Resource<APIResponse>{
        return newsRepository.getNewsHeadlinesByCategory(country, category, page)
    }

}