package com.projects.android.mvvmpagingnewsapp.api

import com.projects.android.mvvmpagingnewsapp.BuildConfig
import com.projects.android.mvvmpagingnewsapp.models.ArticleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    companion object {
        const val BASE_URL = "https://newsapi.org"
    }

    @GET("/v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country") countryCode: String,
        @Query("page") pageNumber: Int,
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_ACCESS_KEY
    ): ArticleResponse

    @GET("/v2/everything")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("page") pageNumber: Int,
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_ACCESS_KEY
    ): ArticleResponse

}