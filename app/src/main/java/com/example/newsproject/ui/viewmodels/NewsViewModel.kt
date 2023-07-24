package com.example.newsproject.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsproject.data.ArticlesItem
import com.example.newsproject.usecases.NewsUseCase
import com.example.newsproject.database.NewsModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NewsViewModel(private val newsUseCase: NewsUseCase): ViewModel() {

    private val _newsFlow = MutableStateFlow<List<ArticlesItem?>?>(emptyList())
    val newsFlow: StateFlow<List<ArticlesItem?>?> get() = _newsFlow
    private val _savedNewsFlow = MutableStateFlow<List<NewsModel>>(emptyList())
    val savedNewsFlow: StateFlow<List<NewsModel>> get() = _savedNewsFlow
    private val _errorFlow = MutableStateFlow(false)
    val errorFlow: StateFlow<Boolean> get() = _errorFlow

    fun getNews(searchQuery: String) {
        viewModelScope.launch {
            try {
                newsUseCase.getNews(searchQuery).collect {
                    if (it.isSuccessful && !it.body()?.articles.isNullOrEmpty()) {
                        _newsFlow.value = it.body()!!.articles
                    } else {
                        _errorFlow.value = true
                    }
                }
            } catch (ex: java.lang.Exception) {
                _errorFlow.value = true
            }
        }
    }

    fun saveArticle(newsModel: NewsModel) {
        viewModelScope.launch {
            newsUseCase.saveArticle(newsModel)
        }
    }

    fun getSavedNews() {
        viewModelScope.launch {
            newsUseCase.getSavedNews().collect {
                _savedNewsFlow.value = it
            }
        }
    }

}