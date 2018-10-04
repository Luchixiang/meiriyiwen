package com.example.passage.model;

import android.graphics.Bitmap;

import com.example.passage.model.scrouse.Article;
import com.example.passage.model.scrouse.ArticleCash;
import com.example.passage.voice.CardComponent;

import java.util.List;

public interface ModelContract {
    interface CallBack {
        void fail();
    }

    interface ArticleCallBack extends CallBack {
        void successOfArticle(String s1, String s2, String s3);
        void successOfAddFavorite();
    }

    interface VoiceCallBack extends CallBack {
        void successOfVoice(List<CardComponent> list, List<Bitmap> bitmaps);

    }

    interface VoicePlayCallBack extends CallBack {
        void suceessOfVoicePlay(String s);
    }
    interface FavoriteCallBack extends CallBack{
        void successOfGetFavorite(List<Article>articles);
    }
    interface cashCallBack extends CallBack{
        void successOfGetCash(List<ArticleCash>articleCashes);
    }
    void getArticle(String url, ArticleCallBack callBack);

    void getVoice(String url, VoiceCallBack callBack);
    void cancelTask();
}
