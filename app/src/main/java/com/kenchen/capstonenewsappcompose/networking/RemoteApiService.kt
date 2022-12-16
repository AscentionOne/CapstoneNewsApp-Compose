package com.kenchen.capstonenewsappcompose.networking

import com.kenchen.capstonenewsappcompose.model.response.GetNewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteApiService {

    @GET("top-headlines")
    suspend fun getTopHeadlinesByCountry(
        @Query("country") country: String,
        @Query("apiKey") apiKey:
        String,
    ): GetNewsResponse
}
