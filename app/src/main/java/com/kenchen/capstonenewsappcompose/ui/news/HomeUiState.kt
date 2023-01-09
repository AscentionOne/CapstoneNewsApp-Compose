package com.kenchen.capstonenewsappcompose.ui.news

import com.kenchen.capstonenewsappcompose.model.Article

sealed interface HomeUiState {
    val isLoading: Boolean
    val errorMessages: String
    val searchInput: String

    data class NoNews(
        override val isLoading: Boolean,
        override val errorMessages: String,
        override val searchInput: String,
    ) : HomeUiState

    data class HasNews(
        val articles: List<Article>,
        val selectedArticle: Article,
        override val isLoading: Boolean,
        override val errorMessages: String,
        override val searchInput: String,
    ) : HomeUiState
}
