package com.example.passage.model;

import android.graphics.Bitmap;

import com.example.passage.voice.CardComponent;

import java.util.List;

public interface ModelContract {
    interface CallBack{
        void fail();
    }
    interface ArticleCallBack extends CallBack{
        void successOfArticle(String s1,String s2,String s3);
    }
    interface VoiceCallBack extends CallBack{
        void successOfVoice(List<CardComponent> list, List<Bitmap> bitmaps);
    }
    interface VoicePlayCallBack extends CallBack{
        void suceessOfVoicePlay(String s);
    }
    void getArticle(String url,ArticleCallBack callBack);
    void getVoice(String url,VoiceCallBack callBack);
}
