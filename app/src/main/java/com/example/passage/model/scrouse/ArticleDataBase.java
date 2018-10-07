package com.example.passage.model.scrouse;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.passage.model.scrouse.articlecash.ArticleCashDao;
import com.example.passage.model.scrouse.articlelike.Article;
import com.example.passage.model.scrouse.articlecash.ArticleCash;
import com.example.passage.model.scrouse.voice.Voice;
import com.example.passage.model.scrouse.articlelike.ArticleDao;
import com.example.passage.model.scrouse.voice.VoiceDao;

@Database(entities = {ArticleCash.class, Article.class, Voice.class},version = 1,exportSchema = false)
public abstract class ArticleDataBase extends RoomDatabase{
    public abstract ArticleDao articleDao();
    public abstract ArticleCashDao articleCashDao();
    public abstract VoiceDao voiceDao();
    private static volatile ArticleDataBase INSTANCE;

   public static ArticleDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ArticleDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ArticleDataBase.class, "word_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
