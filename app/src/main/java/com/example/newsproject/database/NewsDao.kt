package com.example.newsproject.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM dao_model")
    fun getSavedNews(): Flow<List<NewsModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticle(newsModel: NewsModel?)
}