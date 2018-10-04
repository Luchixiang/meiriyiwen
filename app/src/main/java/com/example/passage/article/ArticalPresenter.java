package com.example.passage.article;

import android.app.Application;

import com.example.passage.model.Model;
import com.example.passage.model.ModelContract;
import com.example.passage.model.scrouse.Article;
import com.example.passage.model.scrouse.ArticleCash;

import java.util.List;

public class ArticalPresenter implements ArticalContract.ArticlePresenter {
    private ArticalContract.ArticleView articleView;
    private ModelContract.ArticleCallBack articleCallBack;
    private ModelContract.cashCallBack cashCallBack;
    private Model model;

    @Override
    public void startLoad(String url) {
        model.getArticle(url, articleCallBack);
    }

    @Override
    public void getCash() {
        model.getCash(cashCallBack,articleView.getApplication());
    }

    @Override
    public void addCash(ArticleCash article) {
        model.addCash(articleView.getApplication(),article);
    }

    public ArticalPresenter(ArticalContract.ArticleView articleView) {
        this.articleView = articleView;
        articleView.setPresenter(this);
        creatModel();
    }

    @Override
    public void addFavorite( Article article) {
        model.addFavorite(articleCallBack,article,articleView.getApplication());
    }


    public void creatModel() {
        articleCallBack = new ModelContract.ArticleCallBack() {
            @Override
            public void successOfArticle(String s1, String s2, String s3) {
                articleView.showText(s1, s2, s3);
            }

            @Override
            public void successOfAddFavorite() {
                articleView.showwToast("收藏成功");
            }

            @Override
            public void fail() {
                articleView.showwToast("网络获取失败，请检查网络状况");
            }
        };
        cashCallBack=new ModelContract.cashCallBack() {
            @Override
            public void successOfGetCash(List<ArticleCash> articleCashes) {
                ArticleCash articleCash=articleCashes.get(articleCashes.size()-1);
                String title=articleCash.getArticleTitle();
                String author=articleCash.getArticleAuthor();
                String main=articleCash.getArticleMain();
                articleView.showText(title,author,main);
            }

            @Override
            public void fail() {
                articleView.showwToast("得到本地缓存失败");
            }
        };
        model = new Model();
    }
    public void onDestory()
    {
        articleView=null;
        if (model!=null)
        {
            model.cancelTask();
            model=null;
        }
    }
}
