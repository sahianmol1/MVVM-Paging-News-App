package com.projects.android.mvvmpagingnewsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.projects.android.mvvmpagingnewsapp.R
import com.projects.android.mvvmpagingnewsapp.databinding.ItemArticlePreviewBinding
import com.projects.android.mvvmpagingnewsapp.models.Article

class NewsAdapter(val listener: OnAdapterItemClickListener) : PagingDataAdapter<Article, NewsAdapter.NewsViewHolder>(NEWS_COMPARATOR) {

    var currentPosition :Int? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding =
            ItemArticlePreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }


    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        currentPosition = position
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class NewsViewHolder(private val binding: ItemArticlePreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if(item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(article: Article) {
            binding.apply {
                Glide.with(itemView)
                    .load(article.urlToImage)
                    .error(R.drawable.ic_error)
                    .into(ivArticleImage)

                tvSource.text = article.source?.name
                tvDescription.text = article.description
                tvTitle.text = article.title
                tvPublishedAt.text = article.publishedAt
            }
        }
    }

    companion object {
        private val NEWS_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return true
            }

            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }
        }
    }

    fun currentItem(position: Int) : Article?{
        return getItem(position)
    }
}