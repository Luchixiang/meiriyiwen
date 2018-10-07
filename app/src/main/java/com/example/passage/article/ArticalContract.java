package com.example.passage.article;

import android.app.Application;

import com.example.passage.base.BasePresenter;
import com.example.passage.base.BaseView;
import com.example.passage.model.scrouse.articlelike.Article;
import com.example.passage.model.scrouse.articlecash.ArticleCash;

public interface ArticalContract {
    interface ArticleView extends BaseView<ArticlePresenter> {
        void showText(String title, String author, String main);
        void showwToast(String string);
        Application  getApplication();
        void showFavoriteImg();
        void showDisFavoriteImg();
    }

    interface ArticlePresenter extends BasePresenter {
        void addFavorite( Article article);
        void getCash();
        void addCash(ArticleCash article);
        void deleteFavorite(Article article);
        void QueryFavorite(String title);
    }
}
