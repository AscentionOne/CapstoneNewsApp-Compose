package com.kenchen.capstonenewsappcompose.repository

import android.util.Log
import com.kenchen.capstonenewsappcompose.database.dao.ArticleDao
import com.kenchen.capstonenewsappcompose.database.dao.SourceDao
import com.kenchen.capstonenewsappcompose.model.Article
import com.kenchen.capstonenewsappcompose.model.Source
import com.kenchen.capstonenewsappcompose.networking.NetworkStatusChecker
import com.kenchen.capstonenewsappcompose.networking.RemoteApiImp
import com.kenchen.capstonenewsappcompose.networking.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImp @Inject constructor(
    private val articleDao: ArticleDao,
    private val sourceDao: SourceDao,
    private val remoteApi: RemoteApiImp,
    private val networkStatusChecker: NetworkStatusChecker,
) : NewsRepository {

    companion object {
        private const val TAG = "NewsRepositoryImpl"
    }

    override fun getArticles(): Flow<Result<List<Article>>> {
        return flow<Result<List<Article>>> {
            // delay 1 second to pretend slow network, user will see loading indicator
            delay(1000)

            // always get from local Room database first
            val newsArticles = articleDao.getArticles()

            if (newsArticles.isNotEmpty()) {
                emit(Result.Success(newsArticles))
            }

            Log.i(TAG, "Articles from local database size = ${newsArticles.size}")


            val isOnWifi = networkStatusChecker.isOnWifiConnection()

            println(isOnWifi)
            // only fetch the data when
            // 1. there is wifi connection
            // 2. unclick the has wifi on menu item, means user can use data without wifi

            try {
                println("success")
                val result = remoteApi.getTopHeadlinesBySources("bbc-news")
//                val result = remoteApi.getTopHeadlinesByCountry("us")
                println("success1")

                emit(Result.Success(result))
                println("success2")


                // if network data is not empty, means the source of truth
                // add to database
                if (result.isNotEmpty()) {
                    println("success3")

                    // clear first to prevent stacking up old articles
                    articleDao.clearArticles()
                    articleDao.addArticles(result)
                }
                println("success4")

            } catch (error: Exception) {
                println("Throw error")

                // emit partial to show that data is coming from Room database not from
                // remote API
                emit(Result.Error(error))
                // log the error for team to inspect
                Log.e(TAG, "Articles from local database network error")
            }


        }
    }

    override suspend fun searchArticles(search: String): List<Article> = articleDao
        .searchArticles(search)

    override suspend fun addArticles(articles: List<Article>) = articleDao.addArticles(articles)

    override suspend fun clearArticles() = articleDao.clearArticles()

    override suspend fun getSources(): List<Source> = sourceDao.getSources()

    override suspend fun addSources(sources: List<Source>) = sourceDao.addSources(sources)
}
