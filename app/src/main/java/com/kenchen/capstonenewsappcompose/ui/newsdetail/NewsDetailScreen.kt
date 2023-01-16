package com.kenchen.capstonenewsappcompose.ui.newsdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kenchen.capstonenewsappcompose.model.Article
import com.kenchen.capstonenewsappcompose.ui.news.DummyArticles
import com.kenchen.capstonenewsappcompose.ui.news.HomeUiState
import com.kenchen.capstonenewsappcompose.ui.news.NewsImageGlide
import com.kenchen.capstonenewsappcompose.R

private val defaultSpacer = 16.dp

@Composable
fun NewsDetailScreen(uiState: HomeUiState, modifier: Modifier = Modifier) {
    // if is hasNews ---> display detail
    check(uiState is HomeUiState.HasNews)
    val article = uiState.selectedArticle
    val newsDetailScreenContentDescription =
        stringResource(R.string.news_detail_screen_content_description)
    Column(
        modifier = modifier
            .padding(horizontal = defaultSpacer)
            .semantics { contentDescription = newsDetailScreenContentDescription },
    ) {
        NewsDetailHeaderImage(article = article)
        Spacer(modifier = Modifier.height(defaultSpacer))
        NewsDetailContent(article = article)
    }
}

@Composable
fun NewsDetailHeaderImage(article: Article) {
    NewsImageGlide(
        imageUrl = article.urlToImage,
        modifier = Modifier
            .heightIn(max = 180.dp)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium),
    )
}

@Composable
fun NewsDetailContent(article: Article) {
    Column {
        Text(
            text = article.title,
            style = MaterialTheme.typography.h1,
        )
        Spacer(modifier = Modifier.height(defaultSpacer))
        Text(
            text = article.description ?: "",
            style = MaterialTheme.typography.caption
        )
        Spacer(modifier = Modifier.height(defaultSpacer))
        AuthorLabel(author = article.author ?: "Unknown")
        Spacer(modifier = Modifier.height(defaultSpacer))
        Text(
            text = article.content ?: "",
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
fun AuthorLabel(author: String) {
    Row {
        Image(imageVector = Icons.Filled.AccountCircle, contentDescription = null)
        Text(
            text = author,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewsDetailHeaderImagePreview() {
    NewsDetailHeaderImage(article = DummyArticles.singleArticle)
    Image(painter = painterResource(id = R.drawable.dummy_news_image), contentDescription = null)
}


@Preview(showBackground = true)
@Composable
fun NewsDetailContentPreview() {
    NewsDetailContent(article = DummyArticles.singleArticle)
}
