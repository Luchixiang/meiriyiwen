package com.example.passage.shelf;

import android.app.Application;
import android.os.AsyncTask;

import com.example.passage.articlecash.ArticleCash;

import java.util.List;

public class ArticleRoomRespority {
    private ArticleDao articleDao;
    private List<Article> articles;

    public ArticleRoomRespority(Application application) {
        ArticleRoomDataBase articleRoomDataBase = ArticleRoomDataBase.getDatabase(application);
        articleDao = articleRoomDataBase.articleDao();
        articles = articleDao.getFavorite(true);
    }

    public List<Article> getFavorites() {
        return articles;
    }


    public void deleteFavorite(Article article) {
        new deleteFavoriteAsyncTask(articleDao).execute();
    }

    public void insertFavorite(Article article) {
        new insertFavoriteAsyncTask(articleDao).execute();
    }

    private static class deleteFavoriteAsyncTask extends AsyncTask<Article, Void, Void> {

        private ArticleDao mAsyncTaskDao;

        deleteFavoriteAsyncTask(ArticleDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Article... params) {
            mAsyncTaskDao.deleteFavorite(params[0]);
            return null;
        }
    }

    private static class insertFavoriteAsyncTask extends AsyncTask<Article, Void, Void> {

        private ArticleDao mAsyncTaskDao;

        insertFavoriteAsyncTask(ArticleDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Article... params) {
            mAsyncTaskDao.insertFavorite(params[0]);
            return null;
        }
    }
}
