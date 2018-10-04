package com.example.passage.article;

import android.app.Application;

import com.example.passage.base.BasePresenter;
import com.example.passage.base.BaseView;
import com.example.passage.model.scrouse.Article;
import com.example.passage.model.scrouse.ArticleCash;

public interface ArticalContract {
    interface ArticleView extends BaseView<ArticlePresenter> {
        void showText(String title, String author, String main);
        void showwToast(String string);
        Application  getApplication();
    }

    interface ArticlePresenter extends BasePresenter {
        void addFavorite( Article article);
        void getCash();
        void addCash(ArticleCash article);
    }
}
