package com.projects.android.mvvmpagingnewsapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.projects.android.mvvmpagingnewsapp.api.NewsAPI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(val newsAPI: NewsAPI) {

    fun getBreakingNews(countryCode: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory ={NewsPagingSource(newsAPI, countryCode)}
        ).liveData

    fun searchNews(query: String) = Pager(
            config = PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = false
            ),
            pagingSourceFactory = {SearchNewsPagingSource(newsAPI, query)}
    ).liveData
}