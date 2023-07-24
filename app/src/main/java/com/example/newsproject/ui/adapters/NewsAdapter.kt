package com.example.newsproject.ui.adapters

import android.annotation.SuppressLint
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsproject.data.ArticlesItem
import com.example.newsproject.databinding.NewsItemBinding

class NewsAdapter(
    private val saveClick: ((ArticlesItem) -> Unit)? = null
) : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

    var newsList: List<ArticlesItem?> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        newsList[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(list: List<ArticlesItem?>) {
        this.newsList = list
        notifyDataSetChanged()
    }

    inner class NewsHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArticlesItem) {
            binding.imgSave.setOnClickListener {
                saveClick?.invoke(item)
            }
            binding.txtTitle.text = item.title
            binding.txtDescription.text = item.description
            binding.txtUrl.text = item.url
            Linkify.addLinks(binding.txtUrl, Linkify.WEB_URLS)
            binding.txtPublishedAt.text = item.publishedAt
        }
    }
}