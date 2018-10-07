package com.example.passage.model.scrouse.articlecash;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.passage.model.scrouse.articlecash.ArticleCash;

import java.util.List;

@Dao
public interface ArticleCashDao {
    @Query("SELECT*FROM article_recent")
    List<ArticleCash> getRecentArticle();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecent(ArticleCash articleRecent);

    @Delete
    void deleteRecent(ArticleCash articleRecent);
}
