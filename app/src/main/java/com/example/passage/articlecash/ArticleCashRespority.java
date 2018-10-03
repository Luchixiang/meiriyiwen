package com.example.passage.articlecash;

import android.app.Application;
import android.os.AsyncTask;

public class ArticleCashRespority {
    private ArticleCashDao articleCashDao;
    private ArticleCash articleCash;

    public ArticleCashRespority(Application application) {
        ArticleCashDataBase articleCashDataBase = ArticleCashDataBase.getDatabase(application);
        articleCashDao = articleCashDataBase.articleCashDao();
        articleCash = articleCashDao.getRecentArticle();
    }

    public ArticleCash getArticleCash() {
        return articleCash;
    }

    public void insertCash(ArticleCash article) {
        new insertRecentAsyncTask(articleCashDao).execute();
    }

    public void deleteCash(ArticleCash articleCash) {
        new deleteCashAsyncTask(articleCashDao).execute();
    }

    private static class insertRecentAsyncTask extends AsyncTask<ArticleCash, Void, Void> {

        private ArticleCashDao mAsyncTaskDao;

        insertRecentAsyncTask(ArticleCashDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ArticleCash... params) {
            mAsyncTaskDao.insertRecent(params[0]);
            return null;
        }
    }

    private static class deleteCashAsyncTask extends AsyncTask<ArticleCash, Void, Void> {

        private ArticleCashDao mAsyncTaskDao;

        deleteCashAsyncTask(ArticleCashDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ArticleCash... params) {
            mAsyncTaskDao.deleteRecent(params[0]);
            return null;
        }
    }
}
