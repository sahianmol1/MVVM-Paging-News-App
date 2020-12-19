package com.projects.android.mvvmpagingnewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.projects.android.mvvmpagingnewsapp.R
import com.projects.android.mvvmpagingnewsapp.databinding.FragmentArticleBinding
import com.projects.android.mvvmpagingnewsapp.ui.NewsActivity
import com.projects.android.mvvmpagingnewsapp.viewModel.NewsViewModel

class ArticleFragment : Fragment(R.layout.fragment_article) {
    private val args by navArgs<ArticleFragmentArgs>()
    lateinit var mViewModel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentArticleBinding.bind(view)
        mViewModel = (activity as NewsActivity).mViewModel

        binding.apply {
            val article = args.article
            webView.webViewClient = WebViewClient()
            webView.loadUrl(article.url)

            fab.setOnClickListener {
                mViewModel.upsert(article)
                Snackbar.make(view, "Item Saved Successfully", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}