package com.projects.android.mvvmpagingnewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.projects.android.mvvmpagingnewsapp.R
import com.projects.android.mvvmpagingnewsapp.adapter.NewsAdapter
import com.projects.android.mvvmpagingnewsapp.adapter.NewsLoadStateAdapter
import com.projects.android.mvvmpagingnewsapp.databinding.FragmentBreakingNewsBinding
import com.projects.android.mvvmpagingnewsapp.ui.NewsActivity
import com.projects.android.mvvmpagingnewsapp.viewModel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreakingNewsFragment: Fragment(R.layout.fragment_breaking_news) {

    private lateinit var mViewModel: NewsViewModel

    private var _binding :FragmentBreakingNewsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel = (activity as NewsActivity).mViewModel

        _binding = FragmentBreakingNewsBinding.bind(view)
        val mAdapter = NewsAdapter()

        binding.apply {
            rvBreakingNews.setHasFixedSize(true)
            rvBreakingNews.layoutManager = LinearLayoutManager(activity)
            rvBreakingNews.adapter = mAdapter.withLoadStateHeaderAndFooter(
                header = NewsLoadStateAdapter {mAdapter.retry()},
                footer = NewsLoadStateAdapter {mAdapter.retry()}
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}