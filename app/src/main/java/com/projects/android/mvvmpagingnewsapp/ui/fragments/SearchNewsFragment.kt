package com.projects.android.mvvmpagingnewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.projects.android.mvvmpagingnewsapp.R
import com.projects.android.mvvmpagingnewsapp.adapter.NewsAdapter
import com.projects.android.mvvmpagingnewsapp.adapter.NewsLoadStateAdapter
import com.projects.android.mvvmpagingnewsapp.adapter.OnAdapterItemClickListener
import com.projects.android.mvvmpagingnewsapp.databinding.FragmentSearchNewsBinding
import com.projects.android.mvvmpagingnewsapp.models.Article
import com.projects.android.mvvmpagingnewsapp.ui.NewsActivity
import com.projects.android.mvvmpagingnewsapp.viewModel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_search_news.*

class SearchNewsFragment: Fragment(R.layout.fragment_search_news), OnAdapterItemClickListener {
    lateinit var mViewModel: NewsViewModel
    private var _binding : FragmentSearchNewsBinding? = null
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchNewsBinding.bind(view)
        mViewModel = (activity as NewsActivity).mViewModel

        val mAdapter = NewsAdapter(this)

        binding.apply {

            rvSearchNews.setHasFixedSize(true)
            rvSearchNews.layoutManager = LinearLayoutManager(activity)
            rvSearchNews.adapter = mAdapter.withLoadStateHeaderAndFooter(
                    header = NewsLoadStateAdapter { mAdapter.retry() },
                    footer = NewsLoadStateAdapter { mAdapter.retry() }
            )

            etSearch.addTextChangedListener { editable ->
                if (editable.toString().isNotEmpty()) {
                    mViewModel.searchNews(editable.toString())
                    mViewModel.searchedArticles.observe(viewLifecycleOwner) {
                        mAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                    }
                }

            }
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

    override fun onItemClick(article: Article) {

    }
}