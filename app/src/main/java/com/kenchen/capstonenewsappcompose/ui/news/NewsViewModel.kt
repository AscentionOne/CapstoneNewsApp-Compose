package com.kenchen.capstonenewsappcompose.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenchen.capstonenewsappcompose.model.Article
import com.kenchen.capstonenewsappcompose.networking.Result
import com.kenchen.capstonenewsappcompose.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private data class NewsViewModelState(
    val articles: List<Article>? = null,
    val selectedArticleId: String? = null,
    val isLoading: Boolean = false,
    val errorMessages: String = "",
    val searchInput: String = "",
) {
    fun toUiState(): HomeUiState =
        if (articles == null) {
            HomeUiState.NoNews(
                isLoading = isLoading,
                errorMessages = errorMessages,
                searchInput = searchInput,
            )
        } else {
            HomeUiState.HasNews(
                articles = articles,
                selectedArticle = articles.find { it.title == selectedArticleId }
                    ?: DummyArticles.singleArticle, // need a dummy news
                isLoading = isLoading,
                errorMessages = errorMessages,
                searchInput = searchInput,
            )
        }

}

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepo: NewsRepository,
) : ViewModel() {

    private val viewModelState = MutableStateFlow(NewsViewModelState(isLoading = true))

    val uiState = viewModelState.map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState(),
        )

    init {
        // fetch article initially
        fetchArticle()
    }

    // consume the flow data from repository
    fun fetchArticle() {
        // set loading state
        viewModelState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {
//            newsRepo.getArticles().onEach { articles ->
//                _headLineNewsLiveData.postValue(articles)
//            }.collect()


            newsRepo.getArticles().collect { articles ->
                viewModelState.update {
                    when (articles) {
                        is Result.Error -> it.copy(
                            errorMessages = articles.exception.toString(), // TODO should have custom error message
                            isLoading = false,
                        )
                        is Result.Success -> it.copy(
                            articles = articles.data,
                            isLoading = false,
                        )
                    }
                }
            }


//            newsRepo.getArticles().onEach { articles ->
//                viewModelState.update {
//                    it
//                    when (articles) {
//                        is ArticleState.Partial -> it.copy(
//                            errorMessages = "",
//                            isLoading = false,
//                        )
//                        is ArticleState.Ready -> it.copy(
//                            articles = articles.articles,
//                            isLoading = false,
//                        )
//                    }
//                }
//            }.collect()
        }
    }

    fun selectArticle(id: String) {
        viewModelState.update {
            it.copy(selectedArticleId = id)
        }
    }

    fun onSearchInputChangedThenSearch(searchInput: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val filteredArticle = newsRepo.searchArticles("%$searchInput%")
            viewModelState.update {
                it.copy(
                    searchInput = searchInput,
                    articles = filteredArticle,
                )
            }
        }
    }

    fun onSearchInputChanged(searchInput: String) {
        viewModelState.update {
            it.copy(searchInput = searchInput)
        }
    }

    fun searchArticles(searchInput: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val filteredArticle = newsRepo.searchArticles("%$searchInput%")
            viewModelState.update {
                it.copy(
                    articles = filteredArticle,
                )
            }
        }
    }

    fun fetchArticleFlow(): Flow<Result<List<Article>>> {
        return newsRepo.getArticles()
    }

//    fun fetchArticleStateFlow(): StateFlow<ArticleState> {
//        return newsRepo.getArticles()
//            .stateIn(viewModelScope, SharingStarted.Lazily, PointerEventPass.Initial())
//    }
}
