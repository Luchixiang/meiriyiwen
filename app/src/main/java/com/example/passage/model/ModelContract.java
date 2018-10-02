package com.example.passage.model;

import android.graphics.Bitmap;

import com.example.passage.voice.CardComponent;

import java.util.List;

public interface ModelContract {
    interface CallBack{
        void successOfArticle(String s1,String s2,String s3);
        void fail();
        void successOfVoice(List<CardComponent> list, List<Bitmap> bitmaps);
    }
    void getArticle(String url,CallBack callBack);
    void getVoice(String url,CallBack callBack);
}
