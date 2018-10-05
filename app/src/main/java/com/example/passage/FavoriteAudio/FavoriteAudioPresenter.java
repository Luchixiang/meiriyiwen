package com.example.passage.FavoriteAudio;

import com.example.passage.model.Model;
import com.example.passage.model.ModelContract;
import com.example.passage.voice.Voice;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAudioPresenter implements FavoriteAudioContract.FavoriteAudioPresenter {
    private FavoriteAudioContract.FavoriteAudioView audioView;
    private ModelContract.FavoriteAudioCallBack audioCallBack;
    private List<Voice>cardComponents=new ArrayList<>();
    private Model model;

    public FavoriteAudioPresenter(FavoriteAudioContract.FavoriteAudioView audioView) {
        this.audioView = audioView;
        audioView.setPresenter(this);
        createModel();
    }
    public void createModel()
    {
        audioCallBack=new ModelContract.FavoriteAudioCallBack() {
            @Override
            public void successofGetAudioFavorite(List<Voice> cards) {
                cardComponents.clear();
                cardComponents.addAll(cards);
                audioView.showAudio(cards);
            }

            @Override
            public void fail() {

            }
        };
        model=new Model();
    }
    @Override
    public void getFavoriteAudio() {
        model.getFavoriteAudio(audioCallBack,audioView.gettApplication());
    }

    @Override
    public void addFavoriteAudio(Voice article) {

    }

    @Override
    public void deleteFavoriteAudio(Voice article) {

    }
    @Override
    public void startLoad(String string) {

    }
    @Override
    public void onDestory() {
        audioView=null;
        if (model!=null)
        {
            model.cancelTask();
            model=null;
        }
    }
}
