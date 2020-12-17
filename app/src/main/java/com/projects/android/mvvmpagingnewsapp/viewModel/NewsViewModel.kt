package com.projects.android.mvvmpagingnewsapp.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.projects.android.mvvmpagingnewsapp.data.NewsRepository
import com.projects.android.mvvmpagingnewsapp.models.Article

class NewsViewModel @ViewModelInject constructor(val repository: NewsRepository): ViewModel() {

    private val countryCode = "in"
    lateinit var articles: LiveData<PagingData<Article>>

    fun getBreakingNews() {
        articles = repository.getBreakingNews(countryCode)
    }
}