package com.kenchen.capstonenewsappcompose.networking

import com.kenchen.capstonenewsappcompose.model.Article

interface RemoteApi {
    suspend fun getTopHeadlinesByCountry(countryCode: String): List<Article>

    suspend fun getTopHeadlinesBySources(newsSource: String): List<Article>
}
