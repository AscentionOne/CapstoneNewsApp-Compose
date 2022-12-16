package com.kenchen.capstonenewsappcompose.di

import com.kenchen.capstonenewsappcompose.networking.RemoteApi
import com.kenchen.capstonenewsappcompose.networking.RemoteApiImp
import com.kenchen.capstonenewsappcompose.repository.NewsRepository
import com.kenchen.capstonenewsappcompose.repository.NewsRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindRemoteApi(remoteApiImp: RemoteApiImp): RemoteApi

    @Singleton
    @Binds
    abstract fun bindNewsRepository(newsRepositoryImp: NewsRepositoryImp): NewsRepository
}
