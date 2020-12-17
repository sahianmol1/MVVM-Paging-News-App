package com.projects.android.mvvmpagingnewsapp.data

import androidx.paging.PagingSource
import com.projects.android.mvvmpagingnewsapp.api.NewsAPI
import com.projects.android.mvvmpagingnewsapp.models.Article
import retrofit2.HttpException
import java.io.IOException

const val NEWS_STARTING_PAGE_INDEX = 1

class NewsPagingSource(
    private val newsAPI: NewsAPI,
    private val countryCode: String
) : PagingSource<Int, Article>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: NEWS_STARTING_PAGE_INDEX

        return try {
            val response = newsAPI.getBreakingNews(countryCode, position)
            val articles = response.articles
            LoadResult.Page(
                data = articles,
                prevKey = if (params.key == NEWS_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (articles.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}