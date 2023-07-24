package com.example.newsproject.usecases

import com.example.newsproject.data.NewsResponse
import com.example.newsproject.database.NewsModel
import com.example.newsproject.repo.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class NewsUseCaseImpl(private var newsRepository: NewsRepository): NewsUseCase {
    override suspend fun getNews(searchQuery: String): Flow<Response<NewsResponse>> = flow{
        val data = newsRepository.getNews(searchQuery)
        emit(data)
    }

    override suspend fun saveArticle(newsModel: NewsModel) {
        newsRepository.saveArticle(newsModel)
    }

    override fun getSavedNews(): Flow<List<NewsModel>> {
        return newsRepository.getSavedNews()
    }
}