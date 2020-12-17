package com.projects.android.mvvmpagingnewsapp.repository

import com.projects.android.mvvmpagingnewsapp.api.NewsAPI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(val newsAPI: NewsAPI) {
}