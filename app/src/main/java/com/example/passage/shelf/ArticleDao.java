package com.example.passage.shelf;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ArticleDao {
    @Insert()
     void insertFavorite(Article article);

    @Query("SELECT*FROM article_table WHERE isFavorited LIKE :t")
     List<Article> getFavorite(boolean t);


    @Delete
     void deleteFavorite(Article article);
}
