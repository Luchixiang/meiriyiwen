package com.example.passage.model.scrouse;

import android.app.Application;
import android.os.AsyncTask;

import com.example.passage.voice.Voice;

import java.util.ArrayList;
import java.util.List;

public class ArticleRespority {
    private ArticleDao articleDao;
    private ArticleCashDao articleCashDao;
    private VoiceDao voiceDao;
    private static ArticleRespority INSTANCE = null;
    private List<AsyncTask> asyncTasks = new ArrayList<>();

    public ArticleRespority(Application application) {
        ArticleDataBase articleDataBase = ArticleDataBase.getDatabase(application);
        articleDao = articleDataBase.articleDao();
        articleCashDao = articleDataBase.articleCashDao();
        voiceDao=articleDataBase.voiceDao();
    }

    public static ArticleRespority getINSTANCE(Application application) {
        if (INSTANCE == null) {
            INSTANCE = new ArticleRespority(application);
        }
        return INSTANCE;
    }

    public void getFavorites(resporityCallback resporityCallback) {
        getFavoriteAsyncTask asyncTask = new getFavoriteAsyncTask(articleDao, resporityCallback);
        asyncTasks.add(asyncTask);
        asyncTask.execute();
    }
    public void getVoiceFavorite(resorityVoiceCallback resorityVoiceCallback)
    {
        getFavoriteVoiceAsynTask asynTask=new getFavoriteVoiceAsynTask(voiceDao,resorityVoiceCallback);
        asyncTasks.add(asynTask);
        asynTask.execute();
    }

    public void insertFavorite(Article article) {
        insertFavoriteAsyncTask asyncTask = new ArticleRespority.insertFavoriteAsyncTask(articleDao);
        asyncTasks.add(asyncTask);
        asyncTask.execute(article);
    }
    public void insertVoiceFavorite(Voice voice)
    {
        insertVoiceFavoriteAsynTask asynTask=new insertVoiceFavoriteAsynTask(voiceDao);
        asyncTasks.add(asynTask);
        asynTask.execute(voice);
    }
    private static class getFavoriteAsyncTask extends AsyncTask<Void, Void, Void> {

        private ArticleDao mAsyncTaskDao;
        private resporityCallback resporityCallback;
        private List<Article> articles = new ArrayList<>();

        getFavoriteAsyncTask(ArticleDao dao, resporityCallback resporityCallback) {
            this.resporityCallback = resporityCallback;
            mAsyncTaskDao = dao;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            resporityCallback.favoriteCallBack(articles);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            articles = mAsyncTaskDao.getFavorite();
            return null;
        }
    }
    private static class getFavoriteVoiceAsynTask extends AsyncTask<Void,Void,Void>
    {
        private VoiceDao voiceDao;
        private resorityVoiceCallback resorityVoiceCallback;
        private List<Voice>voices=new ArrayList<>();

        public getFavoriteVoiceAsynTask(VoiceDao voiceDao, ArticleRespority.resorityVoiceCallback resorityVoiceCallback) {
            this.voiceDao = voiceDao;
            this.resorityVoiceCallback = resorityVoiceCallback;
        }

        @Override

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            resorityVoiceCallback.getVoiceCallBack(voices);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            voices=voiceDao.getVoiceFavorite();
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
    private static class insertVoiceFavoriteAsynTask extends AsyncTask<Voice,Void,Void>
    {
        private VoiceDao voiceDao;

        public insertVoiceFavoriteAsynTask(VoiceDao voiceDao) {
            this.voiceDao = voiceDao;
        }

        @Override
        protected Void doInBackground(Voice... voices) {
            voiceDao.insertVoice(voices[0]);
            return null;
        }
    }
    public void getArticleCash(resporityCallback resporityCallback) {
        getCashAsyncTask asyncTask=new getCashAsyncTask(articleCashDao,resporityCallback);
        asyncTasks.add(asyncTask);
        asyncTask.execute();
    }

    private static class getCashAsyncTask extends AsyncTask<Void, Void, Void> {
        private ArticleCashDao articleCashDao;
        private resporityCallback resporityCallback;
        private List<ArticleCash> articleCashes = new ArrayList<>();

        public getCashAsyncTask(ArticleCashDao articleCashDao, resporityCallback resporityCallback) {
            this.articleCashDao = articleCashDao;
            this.resporityCallback = resporityCallback;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            resporityCallback.cashCallBack(articleCashes);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            articleCashes = articleCashDao.getRecentArticle();
            return null;
        }
    }

    public void insertCash(ArticleCash article) {
        insertRecentAsyncTask asyncTask = new insertRecentAsyncTask(articleCashDao);
        asyncTasks.add(asyncTask);
        asyncTask.execute(article);
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

    public interface resporityCallback {
        void favoriteCallBack(List<Article> articles);

        void cashCallBack(List<ArticleCash> articleCashes);
    }
    public interface resorityVoiceCallback{
        void getVoiceCallBack(List<Voice> voices);
    }
    public void deleteTasks() {
        for (AsyncTask asyncTask : asyncTasks) {
            asyncTask.cancel(false);
        }
    }
}
