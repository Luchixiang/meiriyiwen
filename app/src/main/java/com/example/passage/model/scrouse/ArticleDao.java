package com.example.passage.model.scrouse;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(Article article);

    @Query("SELECT*FROM article_table")
    List<Article> getFavorite();


    @Delete
    void deleteFavorite(Article article);
}
