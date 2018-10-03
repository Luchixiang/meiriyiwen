package com.example.passage.shelf;

import android.media.MediaPlayer;
import android.view.Display;

import com.example.passage.model.Model;
import com.example.passage.model.ModelContract;

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
                favoriteArticle.addAll(articles);
                favoriteView.showArticle(articles);
            }

            @Override
            public void successOfAddFavorite() {
                favoriteView.showwToast("收藏成功");
            }

            @Override
            public void successOfDeleteFavorite() {
                favoriteView.showwToast("取消收藏");
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
