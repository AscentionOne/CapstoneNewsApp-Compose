package com.kenchen.capstonenewsappcompose.repository

import com.kenchen.capstonenewsappcompose.model.Article
import com.kenchen.capstonenewsappcompose.model.ArticleState
import com.kenchen.capstonenewsappcompose.model.Source
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getArticles(): Flow<ArticleState>

    fun getArticleByTitle(title: String): Flow<ArticleState>

    suspend fun addArticles(articles: List<Article>)

    suspend fun clearArticles()

    suspend fun searchArticles(search: String): List<Article>

    suspend fun getSources(): List<Source>

    suspend fun addSources(sources: List<Source>)
}
