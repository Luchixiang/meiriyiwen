package com.example.passage.model.scrouse.articlecash;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

    @Entity(tableName = "article_recent")
    public class ArticleCash {
        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "articleTitle")
        private String articleTitle="";
        @ColumnInfo(name = "articleAuthor")
        private String articleAuthor;
        @ColumnInfo(name = "articleMain")
        private String articleMain;

        public String getArticleTitle() {
            return articleTitle;
        }

        public void setArticleTitle(String articleTitle) {
            this.articleTitle = articleTitle;
        }

        public String getArticleAuthor() {
            return articleAuthor;
        }

        public void setArticleAuthor(String articleAuthor) {
            this.articleAuthor = articleAuthor;
        }

        public String getArticleMain() {
            return articleMain;
        }

        public void setArticleMain(String articleMain) {
            this.articleMain = articleMain;
        }
}
