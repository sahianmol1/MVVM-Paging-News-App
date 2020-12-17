package com.projects.android.mvvmpagingnewsapp.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.projects.android.mvvmpagingnewsapp.repository.NewsRepository

class NewsViewModel @ViewModelInject constructor(val repository: NewsRepository): ViewModel() {
}