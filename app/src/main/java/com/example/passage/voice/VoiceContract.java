package com.example.passage.voice;

import android.graphics.Bitmap;

import com.example.passage.base.BasePresenter;
import com.example.passage.base.BaseView;

import java.util.List;

public interface VoiceContract {
    interface VoiceView extends BaseView<VoicePresenter> {
        void listChanged(List<CardComponent> list, List<Bitmap> bitmaps);
    }

    interface VoicePresenter extends BasePresenter {
    }
}
