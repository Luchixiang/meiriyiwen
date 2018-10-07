package com.example.passage.model.scrouse.voice;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.passage.model.scrouse.voice.Voice;

import java.util.List;

@Dao
public interface VoiceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertVoice(Voice voice);

    @Query("SELECT*FROM voice_favorite")
    List<Voice> getVoiceFavorite();


    @Delete
    void deleteVoice(Voice voice);
    @Query("SELECT*FROM voice_favorite WHERE voiceTitle LIKE :title")
    List<Voice> queryVoice(String title);
}
