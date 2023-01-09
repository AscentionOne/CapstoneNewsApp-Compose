package com.kenchen.capstonenewsappcompose.ui.news

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.kenchen.capstonenewsappcompose.CapstoneNewsDestinations
import com.kenchen.capstonenewsappcompose.R
import com.kenchen.capstonenewsappcompose.model.Article
import com.kenchen.capstonenewsappcompose.model.Source
import com.kenchen.capstonenewsappcompose.ui.newsdetail.NewsDetailScreen
import com.kenchen.capstonenewsappcompose.ui.theme.CapstoneNewsAppComposeTheme

@Composable
fun CapstoneNewsApp(
    modifier: Modifier = Modifier,
    newsViewModel: NewsViewModel = viewModel(),
) {

    var canNavigateBack by remember {
        mutableStateOf(false)
    }

    val navController = rememberNavController()

    // add listener to navController to check whether we are in news detail destination or not
    // which is previousBackStackEntry is not null, means we can navigate back
    navController.addOnDestinationChangedListener { navController, _, _ ->
        canNavigateBack = navController.previousBackStackEntry != null
    }

    Scaffold(
        topBar = {
            TopAppBar(canNavigateBack = canNavigateBack,
                navigateUp = { navController.navigateUp() })
        },
    ) { innerPadding ->

        val uiState by newsViewModel.uiState.collectAsState()

        NavHost(navController = navController,
            startDestination = CapstoneNewsDestinations.HOME_ROUTE,
            modifier = modifier.padding(innerPadding)) {
            // Home screen
            composable(route = CapstoneNewsDestinations.HOME_ROUTE) {
                NewsScreen(
                    onNewsClick = {
                        newsViewModel.selectArticle(it)
                        navController.navigate(CapstoneNewsDestinations.NEWS_DETAIL_ROUTE)
                    },
                    uiState = uiState,
                    onRefresh = {
                        newsViewModel.fetchArticle()
                    },
                    searchInput = uiState.searchInput,
                    onSearchInputChanged = {
                        newsViewModel.onSearchInputChangedThenSearch(it)
                    },
                )
            }

            // News detail screen
            composable(route = CapstoneNewsDestinations.NEWS_DETAIL_ROUTE) {
                NewsDetailScreen(uiState = uiState)
            }
        }
    }
}

/**
 * Material 3 top app bar
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    navigateUp: () -> Unit,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(title = {
        Text(text = "Capstone News App Compose")
    }, modifier = modifier, navigationIcon = {
        if (canNavigateBack) {
            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                )
            }
        }
    })
}

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun HomeRoute(
    onNewsClick: (String) -> Unit,
    onRefresh: () -> Unit,
    newsViewModel: NewsViewModel = viewModel(),
) {
    val uiState by newsViewModel.uiState.collectAsStateWithLifecycle()

//    NewsScreen(
//        uiState = uiState,
//        onNewsClick = onNewsClick,
//        onRefresh = onRefresh,
//    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NewsScreen(
    onNewsClick: (String) -> Unit,
    uiState: HomeUiState,
    onRefresh: () -> Unit,
    onSearchInputChanged: (String) -> Unit,
    searchInput: String,
) {

    val articleList: List<Article> = when (uiState) {
        is HomeUiState.NoNews -> emptyList()
        is HomeUiState.HasNews -> uiState.articles
    }

    // experimental Api, can also use focusManager
    val keyBoardController = LocalSoftwareKeyboardController.current
//    val focusManager = LocalFocusManager.current

    Column {
        TextField(
            value = searchInput,
            onValueChange = onSearchInputChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    // we can also perform search when user tap search action
                    keyBoardController?.hide()
//                    focusManager.clearFocus()
                }
            ),
            leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null) },
        )
        LoadingNewsScreenContent(
            isNewsEmpty = when (uiState) {
                is HomeUiState.HasNews -> false
                is HomeUiState.NoNews -> uiState.isLoading
            },
            isLoading = uiState.isLoading,
            emptyContent = { FullScreenLoading() },
            onRefresh = onRefresh,
        ) {
            when (uiState) {
                is HomeUiState.HasNews -> NewsList(articleList = articleList,
                    onCardClick = onNewsClick)
                is HomeUiState.NoNews -> {
                    if (uiState.errorMessages.isEmpty()) {
                        //TODO: not finoshed yet
                        Column(modifier = Modifier) {
                            Text(text = "There are currently no news from remote api. Please try again later!")
                            Button(onClick = {
                                // Re-fetch the article
                            }, Modifier.fillMaxWidth()) {
                                Text("Refresh", textAlign = TextAlign.Center)
                            }
                        }
                    } else {
                        // TODO show error message
                        Text(text = "Network error")
                        // Then the error snack bar will show to let use refresh
                    }
                }
            }

        }
    }

    if (uiState.errorMessages.isNotEmpty()) {
        // TODO show error snackbar
    }
}


/**
 * Lazy column that show list of news
 * */
@Composable
fun NewsList(
    articleList: List<Article>,
    onCardClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)) {

        items(articleList) { news ->
            NewsCard(article = news, onClick = onCardClick)
        }
    }
}

/**
 * Composable that load and show image from url by using Glide
 * */
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsImageGlide(imageUrl: String?, modifier: Modifier = Modifier) {
    if (imageUrl == null) {
        Image(
            painter = painterResource(id = R.drawable.error_placeholder),
            contentDescription = "image error placeholder image",
            modifier = modifier,
            contentScale = ContentScale.Crop,
        )
    } else {
        GlideImage(
            model = imageUrl,
            contentDescription = "",
            modifier = modifier,
            contentScale = ContentScale.Crop,
        )
    }
}

/**
 * Composable of showing loading indicator or list of news with swipe refresh
 * layout when loading the news from remote api
 * */

@Composable
fun LoadingNewsScreenContent(
    isNewsEmpty: Boolean,
    isLoading: Boolean,
    emptyContent: @Composable () -> Unit,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit,
) {
    if (isNewsEmpty) {
        // show loading indicator
        emptyContent()
    } else {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isLoading),
            onRefresh = onRefresh,
            content = content,
        )
    }
}

/**
 * Full screen circular progress indicator, this will be show at the very first time
 * before there are news in the HomeUiState
 */
@Composable
fun FullScreenLoading() {
    Box(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)) {
        androidx.compose.material3.CircularProgressIndicator()
    }
}

@Composable
fun NewsImageCoil(imageUrl: String?, modifier: Modifier = Modifier) {
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        modifier = modifier.size(150.dp),
        contentScale = ContentScale.Crop,
        fallback = painterResource(id = R.drawable.error_placeholder),
    )

}

@Composable
fun NewsImage(
    @DrawableRes image: Int,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(id = R.drawable.alai_photography_unsplash),
        contentDescription = null,
        modifier = modifier.size(150.dp),
        contentScale = ContentScale.Crop,
    )
}

@Composable
fun NewsInfo(title: String, author: String, date: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.h3,
        )
        Text(
            text = author,
            maxLines = 1,
            modifier = Modifier,
            style = MaterialTheme.typography.caption,
        )
        Text(
            text = date,
            style = MaterialTheme.typography.caption,
        )
    }
}

@Composable
fun NewsCard(article: Article, modifier: Modifier = Modifier, onClick: (String) -> Unit) {
    Card(
        modifier = modifier.clickable(onClick = { onClick(article.title) }),
        elevation = 8.dp,
    ) {
        Row(
            modifier = Modifier
                .height(100.dp)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            NewsImageGlide(
                imageUrl = article.urlToImage,
                modifier = Modifier.size(100.dp),
            )
            NewsInfo(title = article.title,
                author = article.author ?: "",
                date = article.publishedAt,
                modifier = Modifier.padding(start = 8.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NewsScreenPreview() {
    CapstoneNewsAppComposeTheme {
//        NewsScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun NewsImagePreview() {
//    NewsImage(R.drawable.alai_photography_unsplash)
}

@Preview(showBackground = true)
@Composable
fun NewsCardPreview() {
//    NewsCard(article = DummyArticles.singleArticle)
}

@Preview(showBackground = true)
@Composable
fun NewsInfoPreview() {
    CapstoneNewsAppComposeTheme {
        NewsInfo(title = "This is a news!", author = "Ken Chen", date = "December 20 2022")
    }
}


object DummyArticles {
    val singleArticle = Article(
        source = Source(
            id = "the-washington-post",
            name = "The Washington Post",
        ),
        author = "Faiz Siddiqui, Cat Zakrzewski, Marisa Iati",
        title = "Twitter to suspend accounts linking to other social media sites - The Washington Post",
        description = "The company owned by Elon Musk said Sunday that it « will no longer allow free promotion of specific social media platforms on Twitter. »",
        url = "https://www.washingtonpost.com/technology/2022/12/18/twitter-policy-links-to-social-sites/",
        urlToImage = "https://www.washingtonpost.com/wp-apps/imrs.php?src=https://arc-anglerfish-washpost-prod-washpost.s3.amazonaws.com/public/LECZVCD7AEI63BZY5VZBPXRHOU.jpg&w=1440",
        publishedAt = "2022-12-19T00:07:39Z",
        content = "Comment on this story\r\nElon Musk apologized and launched a poll asking whether he should step down as head of Twitter on Sunday night after the company launched a new policy that would suspend accoun… [+7238 chars]",
    )
}
