package com.projects.android.mvvmpagingnewsapp.di

import com.projects.android.mvvmpagingnewsapp.api.NewsAPI
import com.projects.android.mvvmpagingnewsapp.api.NewsAPI.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideNewsAPI(retrofit: Retrofit): NewsAPI =
        retrofit.create(NewsAPI::class.java)
}