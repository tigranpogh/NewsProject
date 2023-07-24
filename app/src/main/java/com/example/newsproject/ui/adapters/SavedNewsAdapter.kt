package com.example.newsproject.ui.adapters

import android.annotation.SuppressLint
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsproject.database.NewsModel
import com.example.newsproject.databinding.NewsItemBinding

class SavedNewsAdapter: RecyclerView.Adapter<SavedNewsAdapter.SavedNewsHolder>() {

    var savedNewsList: List<NewsModel?> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedNewsHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedNewsHolder(binding)
    }

    override fun getItemCount(): Int {
        return savedNewsList.size
    }

    override fun onBindViewHolder(holder: SavedNewsHolder, position: Int) {
        savedNewsList[position]?.let { holder.bind(it) }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(list: List<NewsModel?>) {
        this.savedNewsList = list
        notifyDataSetChanged()
    }

    inner class SavedNewsHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewsModel) {
            binding.imgSave.visibility = GONE
            binding.txtTitle.text = item.title
            binding.txtDescription.text = item.description
            binding.txtUrl.text = item.url
            Linkify.addLinks(binding.txtUrl, Linkify.WEB_URLS)
            binding.txtPublishedAt.text = item.publishedAt
        }
    }
}