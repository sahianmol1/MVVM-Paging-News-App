package com.projects.android.mvvmpagingnewsapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.projects.android.mvvmpagingnewsapp.api.NewsAPI
import com.projects.android.mvvmpagingnewsapp.database.ArticleDao
import com.projects.android.mvvmpagingnewsapp.models.Article
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(private val newsAPI: NewsAPI, private val articleDao: ArticleDao) {

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

    suspend fun upsert(article: Article) =
        articleDao.upsert(article)


    suspend fun delete(article: Article) =
        articleDao.delete(article)


    fun getAllArticles()=
        articleDao.getAllArticles()

}