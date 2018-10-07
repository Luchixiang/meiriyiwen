package com.example.passage.FavoriteAudio;

import android.app.Application;

import com.example.passage.base.BasePresenter;
import com.example.passage.base.BaseView;
import com.example.passage.model.scrouse.voice.Voice;

import java.util.List;

public class FavoriteAudioContract {
    interface FavoriteAudioPresenter extends BasePresenter {
        void getFavoriteAudio();
        void addFavoriteAudio(Voice article);
        void deleteFavoriteAudio(Voice article);
    }
    interface FavoriteAudioView extends BaseView<FavoriteAudioPresenter> {
        void showAudio(List<Voice> articles);
        Application gettApplication();
        void showwToast(String string);
    }
}
