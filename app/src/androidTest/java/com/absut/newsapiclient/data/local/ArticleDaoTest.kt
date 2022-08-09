package com.absut.newsapiclient.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.absut.newsapiclient.data.local.dao.ArticleDao
import com.absut.newsapiclient.data.local.db.ArticleDatabase
import com.absut.newsapiclient.data.model.Article
import com.absut.newsapiclient.data.model.Source
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticleDaoTest {

    @get:Rule
    val instantTask = InstantTaskExecutorRule()

    private lateinit var dao: ArticleDao
    private lateinit var database: ArticleDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ArticleDatabase::class.java
        ).build()

        dao = database.getArticleDao()
    }

    @Test
    fun insert_and_get_article_test() = runBlocking {

        val source = Source("source1", "source1")
        val article1 = Article(1, "author1", "content1", "title1", "ur11", source)
        val article2 = Article(2, "author2", "content2", "title2", "ur12", source)
        val article3 = Article(3, "author3", "content3", "title3", "url3", source)
        val articleList = listOf(article1, article2, article3)
        dao.insert(article1)
        dao.insert(article2)
        dao.insert(article3)

        val articlesFromDB = dao.getArticles().first()

        Truth.assertThat(articlesFromDB).isEqualTo(articleList.reversed())
       // assertThat(articlesFromDB, equalTo(articleList.reversed()))
      //  assertEquals(articleList.reversed(),articlesFromDB)
    }

    @Test
    fun deleteAllArticleTest() = runBlocking {
        val source = Source("source1", "source1")
        val articleList = listOf(
            Article(1, "author1", "content1", "title1", "ur11", source),
            Article(2, "author2", "content2", "title2", "ur12", source),
            Article(3, "author3", "content3", "title3", "ur13", source),
        )
        articleList.forEach { article ->
            dao.insert(article)
        }
        dao.deleteAll()
        val articleListFromDB = dao.getArticles().first()

        Truth.assertThat(articleListFromDB).isEmpty()
    }


    @After
    fun tearDown() {
        database.close()
    }
}