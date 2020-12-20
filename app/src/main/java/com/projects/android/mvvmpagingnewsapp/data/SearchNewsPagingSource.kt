package com.projects.android.mvvmpagingnewsapp.data

import androidx.paging.PagingSource
import com.projects.android.mvvmpagingnewsapp.api.NewsAPI
import com.projects.android.mvvmpagingnewsapp.models.Article
import retrofit2.HttpException
import java.io.IOException

const val SEARCH_STARTING_PAGE_INDEX = 1

class SearchNewsPagingSource(
        private val api: NewsAPI,
        private val query: String
): PagingSource<Int, Article>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val position = params.key ?: SEARCH_STARTING_PAGE_INDEX
            val response = api.searchNews(query, position)
            val articles = response.articles
            LoadResult.Page(
                    data = articles,
                    prevKey = if (position == SEARCH_STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if(articles.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}