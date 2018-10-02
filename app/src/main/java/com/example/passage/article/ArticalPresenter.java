package com.example.passage.article;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.passage.model.Model;
import com.example.passage.model.ModelContract;
import com.example.passage.voice.CardComponent;

import java.util.List;

public class ArticalPresenter implements ArticalContract.ArticlePresenter {
    private ArticalContract.ArticleView articleView;
    private Context context;
    private ModelContract.CallBack articleCallBack;
    private Model model;
    @Override
    public void startLoad(String url) {
        model.getArticle(url,articleCallBack);
    }
    public ArticalPresenter(Context context, ArticalContract.ArticleView articleView)
    {
        this.context=context;
        this.articleView=articleView;
        articleView.setPresenter(this);
        creatModel();
    }
    public void creatModel()
    {
        articleCallBack=new ModelContract.CallBack() {
            @Override
            public void successOfArticle(String s1,String s2,String s3) {
                articleView.showText(s1,s2,s3);
            }

            @Override
            public void successOfVoice(List<CardComponent> list, List<Bitmap> bitmaps) {

            }

            @Override
            public void fail() {

            }
        };
        model=new Model(context);
    }
}
