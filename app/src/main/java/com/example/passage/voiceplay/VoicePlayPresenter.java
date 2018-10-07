package com.example.passage.voiceplay;

import com.example.passage.model.Model;
import com.example.passage.model.ModelContract;
import com.example.passage.model.scrouse.voice.Voice;

import java.util.List;

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
            public void successOfAddVoicae(String s) {
                voicePlayView.showToast(s);
                voicePlayView.showIslike();
            }
            @Override
            public void successOfDeleteVoice(String s) {
                voicePlayView.showDisLike();
                voicePlayView.showToast(s);

            }
            @Override
            public void suceessOfVoicePlay(String s) {
                voicePlayView.base64(s);
            }
            @Override
            public void successOfQueryVoice(List<Voice> voices) {
                if (voices==null||voices.size()==0)
                {
                    voicePlayView.showDisLike();
                }
                else
                {
                    voicePlayView.showIslike();
                }
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

    @Override
    public void addVoiceFavorite(Voice voice) {
        model.addVoiceFavorite(voicePlayCallBack,voice,voicePlayView.gettApplication());
    }

    @Override
    public void deleteVoicaeFavorite(Voice voice) {
        model.deleteVoice(voicePlayCallBack,voice,voicePlayView.gettApplication());
    }

    @Override
    public void QueryIfisLiked(String title) {
        model.queryVoice(voicePlayCallBack,title,voicePlayView.gettApplication());
    }

    @Override
    public void onDestory() {

    }
}
