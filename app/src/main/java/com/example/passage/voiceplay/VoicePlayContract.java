package com.example.passage.voiceplay;

import android.app.Application;

import com.example.passage.base.BasePresenter;
import com.example.passage.base.BaseView;
import com.example.passage.model.scrouse.voice.Voice;

public interface VoicePlayContract {
    interface VoicePlayView extends BaseView<VoicePlayContract.VoicePlayPresenter> {
        void base64(String string);
        void showToast(String s);
        Application gettApplication();
        void showIslike();
        void showDisLike();
    }

    interface VoicePlayPresenter extends BasePresenter {
        void addVoiceFavorite(Voice voice);
        void deleteVoicaeFavorite(Voice voice);
        void QueryIfisLiked(String title);
    }
}
