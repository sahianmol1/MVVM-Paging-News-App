package com.projects.android.mvvmpagingnewsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.projects.android.mvvmpagingnewsapp.R
import com.projects.android.mvvmpagingnewsapp.adapter.NewsAdapter
import com.projects.android.mvvmpagingnewsapp.adapter.NewsLoadStateAdapter
import com.projects.android.mvvmpagingnewsapp.adapter.OnAdapterItemClickListener
import com.projects.android.mvvmpagingnewsapp.databinding.FragmentSavedNewsBinding
import com.projects.android.mvvmpagingnewsapp.models.Article
import com.projects.android.mvvmpagingnewsapp.ui.NewsActivity
import com.projects.android.mvvmpagingnewsapp.viewModel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_saved_news.*
import kotlinx.coroutines.launch

class SavedNewsFragment : Fragment(R.layout.fragment_saved_news), OnAdapterItemClickListener {
    lateinit var mViewModel: NewsViewModel
    private var _binding: FragmentSavedNewsBinding? = null
    private val binding get() = _binding!!
    private lateinit var pagedArticles: PagingData<Article>
    private val TAG = "SavedNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel = (activity as NewsActivity).mViewModel

        _binding = FragmentSavedNewsBinding.bind(view)
        val mAdapter = NewsAdapter(this)


        mViewModel.getAllArticles().observe(viewLifecycleOwner, Observer { savedArticles ->
            if (!savedArticles.isNullOrEmpty()) {
                pagedArticles = PagingData.from(savedArticles)
                try {
                    lifecycleScope.launch { mAdapter.submitData(pagedArticles) }
                } catch (e: kotlin.UninitializedPropertyAccessException) {
                    Log.e(TAG, "onViewCreated: ${e.message}")
                }
            }
        })

        val itemTouchCallback = object :ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or  ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position =viewHolder.adapterPosition
                val article = mAdapter.currentItem(position)
                if (article != null) {
                    mViewModel.delete(article)
                }

                Snackbar.make(view, "Item Deleted Successfully", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        if (article != null) {
                            mViewModel.upsert(article)
                        }
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchCallback).apply {
            attachToRecyclerView(rvSavedNews)
        }

        binding.apply {
            rvSavedNews.setHasFixedSize(true)
            rvSavedNews.layoutManager = LinearLayoutManager(activity)
            rvSavedNews.adapter = mAdapter.withLoadStateHeaderAndFooter(
                header = NewsLoadStateAdapter { mAdapter.retry() },
                footer = NewsLoadStateAdapter { mAdapter.retry() }
            )

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(article: Article) {
        val action = SavedNewsFragmentDirections.actionSavedNewsFragment2ToArticleFragment(article)
        findNavController().navigate(action)
    }
}