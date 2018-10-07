package com.example.passage.favoriteArticle;

import com.example.passage.model.Model;
import com.example.passage.model.ModelContract;
import com.example.passage.model.scrouse.articlelike.Article;

import java.util.ArrayList;
import java.util.List;

public class FavoritePresenter implements FavoriteContract.FavoritePresenter{
    private List<Article> favoriteArticle=new ArrayList<>();
    private FavoriteContract.FavoriteView favoriteView;
    private Model model;
    private ModelContract.FavoriteCallBack favoriteCallBack;
    public FavoritePresenter(FavoriteContract.FavoriteView favoriteView)
    {
        this.favoriteView=favoriteView;
        favoriteView.setPresenter(this);
        creatModel();
    }
    public void creatModel()
    {
        favoriteCallBack=new ModelContract.FavoriteCallBack() {
            @Override
            public void successOfGetFavorite(List<Article> articles) {
                favoriteArticle.clear();
                if (articles!=null&&articles.size()!=0)
                {favoriteArticle.addAll(articles);}
                favoriteView.showArticle(articles);
            }
            @Override
            public void fail() {
            }
        };
        model=new Model();
    }
    @Override
    public void startLoad(String string) {
    }

    @Override
    public void onDestory() {
        favoriteView=null;
        if (model!=null)
        {
            model.cancelTask();
            model=null;
        }
    }

    @Override
    public void getFavorite() {
        model.getFavorite(favoriteCallBack,favoriteView.gettApplication());
    }

    @Override
    public void addFavorite(Article article) {

    }

    @Override
    public void deleteFavorite(Article article) {

    }
}
