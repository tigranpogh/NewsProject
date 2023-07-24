package com.example.newsproject.network

import com.example.newsproject.utlis.API_KEY
import com.example.newsproject.data.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/everything")
    suspend fun getNews(
        @Query("q") searchQuery: String,
        @Query("apiKey") apiKey: String = API_KEY,
    ): Response<NewsResponse>
}