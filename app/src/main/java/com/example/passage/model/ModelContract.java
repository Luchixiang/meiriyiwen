package com.example.passage.model;

import android.graphics.Bitmap;

import com.example.passage.model.scrouse.articlelike.Article;
import com.example.passage.model.scrouse.articlecash.ArticleCash;
import com.example.passage.model.scrouse.voice.Voice;

import java.util.List;

public interface ModelContract {
    interface CallBack {
        void fail();
    }

    interface ArticleCallBack extends CallBack {
        void successOfArticle(String s1, String s2, String s3);
        void successOfAddFavorite();
        void successOfDeleteFavorite();
        void successOfQueryArticle(List<Article>articles);
    }

    interface VoiceCallBack extends CallBack {
        void successOfVoice(List<Voice> list, List<Bitmap> bitmaps);

    }

    interface VoicePlayCallBack extends CallBack {
        void suceessOfVoicePlay(String s);
        void successOfAddVoicae(String s);
        void successOfDeleteVoice(String s);
        void successOfQueryVoice(List<Voice>voices);
    }
    interface FavoriteCallBack extends CallBack{
        void successOfGetFavorite(List<Article>articles);
    }
    interface FavoriteAudioCallBack extends CallBack{
        void successofGetAudioFavorite(List<Voice>cards);
    }
    interface cashCallBack extends CallBack{
        void successOfGetCash(List<ArticleCash>articleCashes);
    }
    void getArticle(String url, ArticleCallBack callBack);

    void getVoice(String url, VoiceCallBack callBack);
    void cancelTask();
}
