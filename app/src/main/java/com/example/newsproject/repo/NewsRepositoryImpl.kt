package com.example.newsproject.repo

import com.example.newsproject.data.NewsResponse
import com.example.newsproject.database.NewsModel
import com.example.newsproject.database.NewsDao
import com.example.newsproject.network.NewsApi
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao
) : NewsRepository {
    override suspend fun getNews(searchQuery: String): Response<NewsResponse> {
        return newsApi.getNews(searchQuery)
    }

    override suspend fun saveArticle(newsModel: NewsModel) {
        newsDao.insertArticle(newsModel)
    }

    override fun getSavedNews(): Flow<List<NewsModel>> {
        return newsDao.getSavedNews()
    }
}