package com.kenchen.capstonenewsappcompose.repository

import com.kenchen.capstonenewsappcompose.model.Article
import com.kenchen.capstonenewsappcompose.model.Source
import com.kenchen.capstonenewsappcompose.networking.Result
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getArticles(): Flow<Result<List<Article>>>

    suspend fun addArticles(articles: List<Article>)

    suspend fun clearArticles()

    suspend fun searchArticles(search: String): List<Article>

    suspend fun getSources(): List<Source>

    suspend fun addSources(sources: List<Source>)
}
