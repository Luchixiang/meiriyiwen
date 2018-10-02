package com.example.passage.voiceplay;

import android.graphics.Bitmap;

import com.example.passage.base.BasePresenter;
import com.example.passage.base.BaseView;
import com.example.passage.voice.CardComponent;
import com.example.passage.voice.VoiceContract;

import java.util.List;

public interface VoicePlayContract {
    interface VoicePlayView extends BaseView<VoicePlayContract.VoicePlayPresenter> {
        void base64(String string);
    }

    interface VoicePlayPresenter extends BasePresenter {
    }
}
