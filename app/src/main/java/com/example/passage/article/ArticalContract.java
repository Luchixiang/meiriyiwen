package com.example.passage.article;

import android.content.Context;

import com.example.passage.base.BasePresenter;
import com.example.passage.base.BaseView;

public interface ArticalContract {
    interface ArticleView extends BaseView<ArticlePresenter> {
        void showText(String title,String author,String main);
    }

    interface ArticlePresenter extends BasePresenter {
    }
}
