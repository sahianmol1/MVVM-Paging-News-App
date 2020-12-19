package com.projects.android.mvvmpagingnewsapp.database

import androidx.room.*
import com.projects.android.mvvmpagingnewsapp.models.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Long

    @Delete
    suspend fun delete(article: Article)

    @Query("SELECT * FROM article")
    fun getAllArticles(): List<Article>
}