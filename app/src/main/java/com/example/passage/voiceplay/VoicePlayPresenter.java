package com.example.passage.voiceplay;

import com.example.passage.model.Model;
import com.example.passage.model.ModelContract;

public class VoicePlayPresenter implements VoicePlayContract.VoicePlayPresenter {
    private VoicePlayContract.VoicePlayView voicePlayView;
    private ModelContract.VoicePlayCallBack voicePlayCallBack;
    private Model model;

    public VoicePlayPresenter(VoicePlayContract.VoicePlayView playView) {
        this.voicePlayView = playView;
        voicePlayView.setPresenter(this);
        creatModel();
    }

    public void creatModel() {
        voicePlayCallBack = new ModelContract.VoicePlayCallBack() {
            @Override
            public void suceessOfVoicePlay(String s) {
                voicePlayView.base64(s);
            }

            @Override
            public void fail() {
            }
        };
        model = new Model();
    }

    @Override
    public void startLoad(String string) {
        model.getVoicePlay(string, voicePlayCallBack);
    }
}
