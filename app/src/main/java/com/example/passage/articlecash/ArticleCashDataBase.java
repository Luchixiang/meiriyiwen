package com.example.passage.articlecash;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.passage.shelf.ArticleRoomDataBase;


@Database(entities = {ArticleCash.class}, version = 1)
public abstract class ArticleCashDataBase extends RoomDatabase{
    public abstract ArticleCashDao articleCashDao();

    private static volatile ArticleCashDataBase INSTANCE;

    static ArticleCashDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ArticleRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ArticleCashDataBase.class, "cash_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
