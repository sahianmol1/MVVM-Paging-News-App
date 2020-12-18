package com.projects.android.mvvmpagingnewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.projects.android.mvvmpagingnewsapp.R
import com.projects.android.mvvmpagingnewsapp.adapter.NewsAdapter
import com.projects.android.mvvmpagingnewsapp.adapter.NewsLoadStateAdapter
import com.projects.android.mvvmpagingnewsapp.databinding.FragmentBreakingNewsBinding
import com.projects.android.mvvmpagingnewsapp.ui.NewsActivity
import com.projects.android.mvvmpagingnewsapp.viewModel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    //    private val mViewModel by viewModels<NewsViewModel>()
    lateinit var mViewModel: NewsViewModel

    private var _binding: FragmentBreakingNewsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel = (activity as NewsActivity).mViewModel

        _binding = FragmentBreakingNewsBinding.bind(view)
        val mAdapter = NewsAdapter()

        mViewModel.getBreakingNews()
        mViewModel.articles.observe(viewLifecycleOwner) {
            mAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        binding.apply {
            rvBreakingNews.setHasFixedSize(true)
            rvBreakingNews.layoutManager = LinearLayoutManager(activity)
            rvBreakingNews.adapter = mAdapter.withLoadStateHeaderAndFooter(
                header = NewsLoadStateAdapter { mAdapter.retry() },
                footer = NewsLoadStateAdapter { mAdapter.retry() }
            )
        }

        mAdapter.addLoadStateListener { loadState ->
            if (loadState.append.endOfPaginationReached) {
                Snackbar.make(view, "End Reached", Snackbar.LENGTH_SHORT).show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}