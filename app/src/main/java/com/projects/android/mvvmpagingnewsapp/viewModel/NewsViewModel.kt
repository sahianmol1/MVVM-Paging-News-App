package com.projects.android.mvvmpagingnewsapp.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.projects.android.mvvmpagingnewsapp.data.NewsRepository
import com.projects.android.mvvmpagingnewsapp.models.Article
import kotlinx.coroutines.launch

class NewsViewModel @ViewModelInject constructor(val repository: NewsRepository): ViewModel() {

    private val countryCode = "us"
    lateinit var articles: LiveData<PagingData<Article>>

    fun getBreakingNews() {
        articles = repository.getBreakingNews(countryCode)
    }

    lateinit var searchedArticles: LiveData<PagingData<Article>>

    fun searchNews(query: String) {
        searchedArticles = repository.searchNews(query)
    }

     fun upsert(article: Article) = viewModelScope.launch { repository.upsert(article) }


     fun delete(article: Article) = viewModelScope.launch { repository.delete(article) }



    fun getAllArticles(): LiveData<List<Article>> =
         repository.getAllArticles()

}