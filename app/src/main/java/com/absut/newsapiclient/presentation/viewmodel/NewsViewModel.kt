package com.absut.newsapiclient.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.absut.newsapiclient.data.model.APIResponse
import com.absut.newsapiclient.data.model.Article
import com.absut.newsapiclient.domain.usecase.*
import com.absut.newsapiclient.utils.Constants
import com.absut.newsapiclient.utils.Resource
import com.absut.newsapiclient.utils.Utils.Companion.isNetworkAvailable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class NewsViewModel(
    private val app: Application,
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    private val getNewsHeadlinesByCategoryUseCase: GetNewsHeadlinesByCategoryUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase,
    private val deleteAllSavedNewsUseCase: DeleteAllSavedNewsUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase
) : AndroidViewModel(app) {


    val country = Constants.QUERY_COUNTRY
    val page = 1


    init {
        getNewsHeadlines(country, page)
    }


    //////////////////////////////get news headlines //////////////////////////////////

    val newsHeadlines: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

     fun getNewsHeadlines(country: String, page: Int) = viewModelScope.launch(Dispatchers.IO) {
         newsHeadlines.postValue(Resource.Loading())
         try {
             if (isNetworkAvailable(app)) {
                 val apiResult = getNewsHeadlinesUseCase.execute(country, page)
                 newsHeadlines.postValue(apiResult)
             } else {
                 newsHeadlines.postValue(Resource.Error("No internet connection"))
             }
         } catch (e: Exception) {
             newsHeadlines.postValue(Resource.Error(e.message.toString()))
         }
     }

    //////////////////////////////get news headlines by category //////////////////////////////////

    val newsHeadlinesCategory: MutableLiveData<Resource<APIResponse>> = MutableLiveData()
    val currentCategory: MutableLiveData<String> = MutableLiveData()

    init {
        currentCategory.value = Constants.QUERY_CATEGORY_TECHNOLOGY
        getNewsHeadlinesByCategory()
    }

    fun getNewsHeadlinesByCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            newsHeadlinesCategory.postValue(Resource.Loading())
            try {
                if (isNetworkAvailable(app)) {
                    val apiResult = getNewsHeadlinesByCategoryUseCase.execute(
                        country,
                        currentCategory.value.toString(),
                        page
                    )
                    newsHeadlinesCategory.postValue(apiResult)
                } else {
                    newsHeadlinesCategory.postValue(Resource.Error("No internet connection"))
                }
            } catch (e: Exception) {
                newsHeadlinesCategory.postValue(Resource.Error(e.message.toString()))
            }

        }
    }


    //////////////////////////////get searched news//////////////////////////////////

    val searchedNews: MutableLiveData<Resource<APIResponse>> = MutableLiveData()
    val searchQuery: MutableLiveData<String> = MutableLiveData()

    fun searchNews() {
        viewModelScope.launch(Dispatchers.IO) {
              searchedNews.postValue(Resource.Loading())
              try {
                  if (isNetworkAvailable(app)) {
                      val response = getSearchedNewsUseCase.execute(searchQuery.value.toString(), page)
                      searchedNews.postValue(response)
                  } else {
                      searchedNews.postValue(Resource.Error("No internet connection"))
                  }
              } catch (e: Exception) {
                  searchedNews.postValue(Resource.Error(e.message.toString()))
              }
        }
    }


    //////////////selected article/////////////////////////////////
    private val mutableSelectedArticle = MutableLiveData<Article>()
    val selectedArticle: LiveData<Article> get() = mutableSelectedArticle

    fun selectItem(article: Article) {
        mutableSelectedArticle.value = article
    }


    /////////////////////////Save news//////////////////////////

    fun saveNews(article: Article) = viewModelScope.launch() {
        saveNewsUseCase.execute(article)
    }


    /////////////////////////delete news//////////////////////////

    fun deleteNews(article: Article) = viewModelScope.launch() {
        val rowId = deleteSavedNewsUseCase.execute(article)
    }

    /////////////////////////get Saved news//////////////////////////


    fun getSavedNewsList() = liveData {
        getSavedNewsUseCase.execute().collect {
            emit(it)
        }
    }

    /////////////////////////delete ALL news//////////////////////////

    fun deleteAllNews() = viewModelScope.launch() {
        deleteAllSavedNewsUseCase.execute()
    }


}