package com.example.passage.favoriteArticle;

import android.app.Application;

import com.example.passage.base.BasePresenter;
import com.example.passage.base.BaseView;
import com.example.passage.model.scrouse.articlelike.Article;

import java.util.List;

public interface FavoriteContract {
    interface FavoritePresenter extends BasePresenter{
        void getFavorite();
        void addFavorite(Article article);
        void deleteFavorite(Article article);
    }
    interface FavoriteView extends BaseView<FavoritePresenter>{
        void showArticle(List<Article> articles);
        Application gettApplication();
        void showwToast(String string);
    }
}
