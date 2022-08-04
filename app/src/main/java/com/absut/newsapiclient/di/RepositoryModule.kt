package com.absut.newsapiclient.di

import com.absut.newsapiclient.data.repository.NewsRepositoryImpl
import com.absut.newsapiclient.data.repository.datasource.NewsLocalDataSource
import com.absut.newsapiclient.data.repository.datasource.NewsRemoteDataSource
import com.absut.newsapiclient.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideNewsRepository(
        remoteDataSource: NewsRemoteDataSource,
        localDataSource: NewsLocalDataSource
    ): NewsRepository {
        return NewsRepositoryImpl(remoteDataSource, localDataSource)
    }

}