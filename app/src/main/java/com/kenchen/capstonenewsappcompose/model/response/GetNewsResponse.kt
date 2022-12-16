package com.kenchen.capstonenewsappcompose.model.response

import com.kenchen.capstonenewsappcompose.model.Article
import com.squareup.moshi.Json

data class GetNewsResponse(
    @field:Json(name = "articles") val articles: List<Article> = mutableListOf(),
)
