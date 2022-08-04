package com.absut.newsapiclient.di

import com.absut.newsapiclient.data.local.dao.ArticleDao
import com.absut.newsapiclient.data.repository.datasource.NewsLocalDataSource
import com.absut.newsapiclient.data.repository.datasourceImpl.NewsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Provides
    @Singleton
    fun provideLocalDataSource(articleDao: ArticleDao): NewsLocalDataSource {
        return NewsLocalDataSourceImpl(articleDao)
    }
}