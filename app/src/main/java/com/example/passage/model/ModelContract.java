package com.example.passage.model;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.passage.shelf.Article;
import com.example.passage.voice.CardComponent;

import java.util.List;

public interface ModelContract {
    interface CallBack {
        void fail();
    }

    interface ArticleCallBack extends CallBack {
        void successOfArticle(String s1, String s2, String s3);
    }

    interface VoiceCallBack extends CallBack {
        void successOfVoice(List<CardComponent> list, List<Bitmap> bitmaps);
    }

    interface VoicePlayCallBack extends CallBack {
        void suceessOfVoicePlay(String s);
    }
    interface FavoriteCallBack extends CallBack{
        void successOfGetFavorite(List<Article>articles);
        void successOfAddFavorite();
        void successOfDeleteFavorite();
    }
    void getArticle(String url, ArticleCallBack callBack);

    void getVoice(String url, VoiceCallBack callBack);
}
