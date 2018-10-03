package com.example.passage.articlecash;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface ArticleCashDao {
    @Query("SELECT*FROM article_recent")
    public ArticleCash getRecentArticle();
    @Insert
    public void insertRecent(ArticleCash articleRecent);
    @Delete
    public void deleteRecent(ArticleCash articleRecent);
}
