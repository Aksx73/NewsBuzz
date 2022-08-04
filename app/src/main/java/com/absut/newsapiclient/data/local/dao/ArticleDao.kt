package com.absut.newsapiclient.data.local.dao

import androidx.room.*
import com.absut.newsapiclient.data.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article): Long

    @Delete
    suspend fun delete(article: Article)

    @Query("DELETE FROM articles")
    suspend fun deleteAll()

    @Query("SELECT * FROM articles ORDER BY id DESC")
    fun getArticles(): Flow<List<Article>>


}