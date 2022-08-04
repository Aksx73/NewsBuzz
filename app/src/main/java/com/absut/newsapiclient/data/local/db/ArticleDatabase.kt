package com.absut.newsapiclient.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.absut.newsapiclient.data.local.Converters
import com.absut.newsapiclient.data.local.dao.ArticleDao
import com.absut.newsapiclient.data.model.Article

@Database(entities = [Article::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase(){

    abstract fun getArticleDao(): ArticleDao

}