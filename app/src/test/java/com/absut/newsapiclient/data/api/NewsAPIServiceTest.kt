package com.absut.newsapiclient.data.api

import com.absut.newsapiclient.data.remote.NewsAPIService
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NewsAPIServiceTest {
    private lateinit var service: NewsAPIService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder().baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(NewsAPIService::class.java)
    }


    private fun enqueueMockResponse(filename: String) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(filename)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }


    @Test
    fun getTopHeadlines_sentRequest_receiveExpected() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val response = service.getTopHeadlinesByCountry("us", 1).body()
            val request = server.takeRequest()
            Truth.assertThat(response).isNotNull()
            Truth.assertThat(request.path).isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=b51a0781fcbf499b96d779d36f0aee46")
        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctPageSize(){
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val response = service.getTopHeadlinesByCountry("us", 1).body()
            val articles = response!!.articleDtos
            
            Truth.assertThat(articles.size).isEqualTo(20)
        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctContent(){
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val response = service.getTopHeadlinesByCountry("us", 1).body()
            val articles = response!!.articleDtos
            val article = articles[0]
            Truth.assertThat(article.author).isEqualTo("Bryan Pietsch")
            Truth.assertThat(article.url).isEqualTo("https://www.washingtonpost.com/nation/2022/07/24/iowa-campground-shooting-schmidt-family/")
            Truth.assertThat(article.publishedAt).isEqualTo("2022-07-24T12:45:45Z")

        }
    }


    @After
    fun tearDown() {
        server.shutdown()
    }


}

