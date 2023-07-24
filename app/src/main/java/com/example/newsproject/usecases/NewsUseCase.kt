package com.example.newsproject.usecases

import com.example.newsproject.data.NewsResponse
import com.example.newsproject.database.NewsModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NewsUseCase {
    suspend fun getNews(searchQuery: String): Flow<Response<NewsResponse>>
    suspend fun saveArticle(newsModel: NewsModel)
    fun getSavedNews(): Flow<List<NewsModel>>
}