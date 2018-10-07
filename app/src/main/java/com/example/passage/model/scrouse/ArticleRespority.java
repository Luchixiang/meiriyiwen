package com.example.passage.model.scrouse;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.example.passage.model.scrouse.articlecash.ArticleCashDao;
import com.example.passage.model.scrouse.articlelike.Article;
import com.example.passage.model.scrouse.articlecash.ArticleCash;
import com.example.passage.model.scrouse.voice.Voice;
import com.example.passage.model.scrouse.articlelike.ArticleDao;
import com.example.passage.model.scrouse.voice.VoiceDao;

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
        voiceDao = articleDataBase.voiceDao();
    }

    //得到仓库实例
    public static ArticleRespority getINSTANCE(Application application) {
        if (INSTANCE == null) {
            INSTANCE = new ArticleRespority(application);
        }
        return INSTANCE;
    }

    //实现文章收藏
    public void insertFavorite(Article article) {
        insertFavoriteAsyncTask asyncTask = new ArticleRespority.insertFavoriteAsyncTask(articleDao);
        asyncTasks.add(asyncTask);
        asyncTask.execute(article);
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

    //得到文章收藏列表
    public void getFavorites(resporityCallback resporityCallback) {
        getFavoriteAsyncTask asyncTask = new getFavoriteAsyncTask(articleDao, resporityCallback);
        asyncTasks.add(asyncTask);
        asyncTask.execute();
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


    //查询是否已经收藏了文章
    public void queryIfLikeArticle(queryArticleCallBack queryArticleCallBack, String title) {
        queryArticleAsyncTask asyncTask = new queryArticleAsyncTask(articleDao, queryArticleCallBack);
        asyncTasks.add(asyncTask);
        asyncTask.execute(title);
    }

    public static class queryArticleAsyncTask extends AsyncTask<String, Void, Void> {
        private ArticleDao articleDao;
        private queryArticleCallBack queryArticleCallBack;
        private List<Article> articles;

        public queryArticleAsyncTask(ArticleDao articleDao, ArticleRespority.queryArticleCallBack queryArticleCallBack) {
            this.articleDao = articleDao;
            this.queryArticleCallBack = queryArticleCallBack;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            queryArticleCallBack.queryArtyicle(articles);
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(String... strings) {
            articles = articleDao.getIfisLike(strings[0]);
            return null;
        }
    }

    //实现删除文章收藏
    public void deleteArticle(Article article) {
        deleteArticleAsynTask asynTask = new deleteArticleAsynTask(articleDao);
        asyncTasks.add(asynTask);
        asynTask.execute(article);
    }

    public static class deleteArticleAsynTask extends AsyncTask<Article, Void, Void> {
        private ArticleDao articleDao;

        public deleteArticleAsynTask(ArticleDao articleDao) {
            this.articleDao = articleDao;
        }

        @Override
        protected Void doInBackground(Article... articles) {
            articleDao.deleteFavorite(articles[0]);
            return null;
        }
    }

    //实现添加文章缓存
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


    //得到文章缓存
    public void getArticleCash(resporityCallback resporityCallback) {
        getCashAsyncTask asyncTask = new getCashAsyncTask(articleCashDao, resporityCallback);
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


    //添加声音收藏
    public void insertVoiceFavorite(Voice voice) {
        insertVoiceFavoriteAsynTask asynTask = new insertVoiceFavoriteAsynTask(voiceDao);
        asyncTasks.add(asynTask);
        asynTask.execute(voice);
    }

    private static class insertVoiceFavoriteAsynTask extends AsyncTask<Voice, Void, Void> {
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

    //得到声音收藏列表
    public void getVoiceFavorite(resorityVoiceCallback resorityVoiceCallback) {
        getFavoriteVoiceAsynTask asynTask = new getFavoriteVoiceAsynTask(voiceDao, resorityVoiceCallback);
        asyncTasks.add(asynTask);
        asynTask.execute();
    }

    private static class getFavoriteVoiceAsynTask extends AsyncTask<Void, Void, Void> {
        private VoiceDao voiceDao;
        private resorityVoiceCallback resorityVoiceCallback;
        private List<Voice> voices = new ArrayList<>();

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
            voices = voiceDao.getVoiceFavorite();
            return null;
        }
    }


    //查询是否已收藏声音
    public void queryIfLikeVocie(queryVoiceCallBack queryVoiceCallBack, String title) {
        queryIfLikeVoiceTask task = new queryIfLikeVoiceTask(queryVoiceCallBack, voiceDao);
        asyncTasks.add(task);
        task.execute(title);
    }

    public static class queryIfLikeVoiceTask extends AsyncTask<String, Void, Void> {
        private queryVoiceCallBack queryVoiceCallBack;
        private VoiceDao voiceDao;
        private List<Voice> voices = new ArrayList<>();

        public queryIfLikeVoiceTask(ArticleRespority.queryVoiceCallBack queryVoiceCallBack, VoiceDao voiceDao) {
            this.queryVoiceCallBack = queryVoiceCallBack;
            this.voiceDao = voiceDao;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            queryVoiceCallBack.queryVoice(voices);
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(String... strings) {
            voices = voiceDao.queryVoice(strings[0]);
            return null;
        }
    }

    //删除收藏得声音
    public void deleteVoice(Voice voice) {
        deleteVoiceAsynTask asynTask = new deleteVoiceAsynTask(voiceDao);
        asyncTasks.add(asynTask);
        asynTask.execute(voice);
    }

    public static class deleteVoiceAsynTask extends AsyncTask<Voice, Void, Void> {
        private VoiceDao voiceDao;

        public deleteVoiceAsynTask(VoiceDao voiceDao) {
            this.voiceDao = voiceDao;
        }

        @Override
        protected Void doInBackground(Voice... voices) {
            voiceDao.deleteVoice(voices[0]);
            return null;
        }
    }

    //回调函数
    public interface resporityCallback {
        void favoriteCallBack(List<Article> articles);

        void cashCallBack(List<ArticleCash> articleCashes);
    }

    public interface resorityVoiceCallback {
        void getVoiceCallBack(List<Voice> voices);
    }

    public interface queryArticleCallBack {
        void queryArtyicle(List<Article> articles);
    }

    public interface queryVoiceCallBack {
        void queryVoice(List<Voice> voices);
    }

    //销毁任务
    public void deleteTasks() {
        for (AsyncTask asyncTask : asyncTasks) {
            asyncTask.cancel(false);
        }
    }
}
