package com.example.newsproject.di

import android.app.Application
import androidx.room.Room
import androidx.viewbinding.BuildConfig
import com.example.newsproject.utlis.BASE_URL
import com.example.newsproject.database.AppDatabase
import com.example.newsproject.database.NewsDao
import com.example.newsproject.network.NewsApi
import com.example.newsproject.repo.NewsRepository
import com.example.newsproject.repo.NewsRepositoryImpl
import com.example.newsproject.ui.viewmodels.NewsViewModel
import com.example.newsproject.usecases.NewsUseCase
import com.example.newsproject.usecases.NewsUseCaseImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
} else OkHttpClient
    .Builder()
    .build()

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

private fun provideApiService(retrofit: Retrofit): NewsApi =
    retrofit.create(NewsApi::class.java)

private fun provideDatabase(application: Application): AppDatabase {
    return Room.databaseBuilder(application, AppDatabase::class.java, "database").build()
}

private fun provideDao(appDatabase: AppDatabase): NewsDao? {
    return appDatabase.newsDao()
}

val appModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    single { provideApiService(get()) }

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}

val newsModule = module {
    single<NewsRepository> {
        return@single NewsRepositoryImpl(get(), get())
    }

    single<NewsUseCase> {
        return@single NewsUseCaseImpl(get())
    }

    viewModel {
        NewsViewModel(get())
    }
}