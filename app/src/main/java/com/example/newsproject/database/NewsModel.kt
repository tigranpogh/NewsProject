package com.example.newsproject.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "dao_model", indices = [Index(value = ["title"], unique = true)])
data class NewsModel (
    @PrimaryKey
    val title: String,
    val description: String,
    val url: String,
    val publishedAt: String
)