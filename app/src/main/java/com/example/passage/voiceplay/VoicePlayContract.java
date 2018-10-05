package com.example.passage.voiceplay;

import android.app.Application;

import com.example.passage.base.BasePresenter;
import com.example.passage.base.BaseView;
import com.example.passage.model.scrouse.Article;
import com.example.passage.voice.Voice;

public interface VoicePlayContract {
    interface VoicePlayView extends BaseView<VoicePlayContract.VoicePlayPresenter> {
        void base64(String string);
        void showToast(String s);
        Application gettApplication();
    }

    interface VoicePlayPresenter extends BasePresenter {
        void addVoiceFavorite(Voice voice);
    }
}
