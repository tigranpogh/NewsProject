package com.example.newsproject.repo

import com.example.newsproject.data.NewsResponse
import com.example.newsproject.database.NewsModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NewsRepository {
    suspend fun getNews(searchQuery: String): Response<NewsResponse>
    suspend fun saveArticle(newsModel: NewsModel)
    fun getSavedNews(): Flow<List<NewsModel>>
}