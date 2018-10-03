package com.example.passage.article;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.passage.model.Model;
import com.example.passage.model.ModelContract;
import com.example.passage.voice.CardComponent;

import java.util.List;

public class ArticalPresenter implements ArticalContract.ArticlePresenter {
    private ArticalContract.ArticleView articleView;
    private ModelContract.ArticleCallBack articleCallBack;
    private Model model;
    @Override
    public void startLoad(String url) {
        model.getArticle(url,articleCallBack);
    }
    public ArticalPresenter( ArticalContract.ArticleView articleView)
    {
        this.articleView=articleView;
        articleView.setPresenter(this);
        creatModel();
    }
    public void creatModel()
    {
        articleCallBack=new ModelContract.ArticleCallBack() {
            @Override
            public void successOfArticle(String s1,String s2,String s3) {
                articleView.showText(s1,s2,s3);
            }
            @Override
            public void fail() {

            }
        };
        model=new Model();
    }
}
