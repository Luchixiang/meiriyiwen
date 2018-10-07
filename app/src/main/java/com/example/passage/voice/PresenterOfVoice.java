package com.example.passage.voice;

import android.graphics.Bitmap;

import com.example.passage.model.Model;
import com.example.passage.model.ModelContract;
import com.example.passage.model.scrouse.voice.Voice;

import java.util.ArrayList;
import java.util.List;

public class PresenterOfVoice implements VoiceContract.VoicePresenter {
    private VoiceContract.VoiceView voiceView;
    private ModelContract.VoiceCallBack voiceCallback;
    private Model model;
    private List<Voice> mCards = new ArrayList<>();
    private List<Bitmap> bitmaps = new ArrayList<>();

    public PresenterOfVoice(VoiceContract.VoiceView voiceView) {
        this.voiceView = voiceView;
        voiceView.setPresenter(this);
        creatModel();
    }

    @Override
    public void startLoad(String string) {
        model.getVoice(string, voiceCallback);
    }

    @Override
    public void onDestory() {
        voiceView=null;
        if (model!=null)
        {
            model.cancelTask();
            model=null;
        }
    }

    public void creatModel() {
        voiceCallback = new ModelContract.VoiceCallBack() {

            @Override
            public void successOfVoice(List<Voice> list, List<Bitmap> imgs) {
                mCards.clear();
                mCards.addAll(list);
                bitmaps.clear();
                bitmaps.addAll(imgs);
                voiceView.listChanged(list, imgs);
            }

            @Override
            public void fail() {
            }
        };
        model = new Model();
    }

}
