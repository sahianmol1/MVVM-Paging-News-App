package com.projects.android.mvvmpagingnewsapp.adapter

import com.projects.android.mvvmpagingnewsapp.models.Article

interface OnAdapterItemClickListener {
    fun onItemClick(article: Article)
}