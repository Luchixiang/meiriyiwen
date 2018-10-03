package com.example.passage.shelf;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Article.class}, version = 1)
public abstract class ArticleRoomDataBase extends RoomDatabase {
    public abstract ArticleDao articleDao();

    private static volatile ArticleRoomDataBase INSTANCE;

    static ArticleRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ArticleRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ArticleRoomDataBase.class, "word_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
