package com.example.passage.model.scrouse;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {ArticleCash.class, Article.class},version = 1,exportSchema = false)
public abstract class ArticleDataBase extends RoomDatabase{
    public abstract ArticleDao articleDao();
    public abstract ArticleCashDao articleCashDao();
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
