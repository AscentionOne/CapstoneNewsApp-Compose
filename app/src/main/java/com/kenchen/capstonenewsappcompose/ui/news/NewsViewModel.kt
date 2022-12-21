package com.kenchen.capstonenewsappcompose.ui.news

import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.*
import com.kenchen.capstonenewsappcompose.model.ArticleState
import com.kenchen.capstonenewsappcompose.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepo: NewsRepository,
) : ViewModel() {

    private val _headLineNewsLiveData = MutableLiveData<ArticleState>()
    val headLineNewsLiveData: LiveData<ArticleState> = _headLineNewsLiveData

    init {
        // fetch article initially
        fetchArticle()
    }

    // consume the flow data from repository
    fun fetchArticle() {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepo.getArticles().onEach { articles ->
                _headLineNewsLiveData.postValue(articles)
            }.collect()
        }
    }

    fun searchArticles(search: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val filteredArticle = newsRepo.searchArticles("%$search%")
            _headLineNewsLiveData.postValue(ArticleState.Ready(filteredArticle))
        }
    }

    fun fetchArticleFlow(): Flow<ArticleState> {
        return newsRepo.getArticles()
    }

    fun fetchArticleStateFlow(): StateFlow<ArticleState> {
        return newsRepo.getArticles().stateIn(viewModelScope, SharingStarted.Lazily, ArticleState.Initial())
    }
}
