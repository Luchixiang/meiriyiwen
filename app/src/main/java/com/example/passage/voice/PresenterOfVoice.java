package com.example.passage.voice;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.passage.model.Model;
import com.example.passage.model.ModelContract;

import java.util.ArrayList;
import java.util.List;

public class PresenterOfVoice implements VoiceContract.VoicePresenter {
    private VoiceContract.VoiceView voiceView;
    private Context context;
    private ModelContract.CallBack voiceCallback;
    private Model model;
    private List<CardComponent>mCards=new ArrayList<>();
    private List<Bitmap>bitmaps=new ArrayList<>();
    public PresenterOfVoice(Context context, VoiceContract.VoiceView voiceView)
    {
        this.voiceView=voiceView;
        this.context=context;
        voiceView.setPresenter(this);
        creatModel();
    }
    @Override
    public void startLoad(String string) {
        model.getVoice(string,voiceCallback);
    }
    public void creatModel()
    {
        voiceCallback=new ModelContract.CallBack() {
            @Override
            public void successOfArticle(String s1, String s2, String s3) {
            }

            @Override
            public void successOfVoice(List<CardComponent> list, List<Bitmap> imgs) {
                mCards.clear();
                mCards.addAll(list);
                bitmaps.clear();
                bitmaps.addAll(imgs);
                voiceView.listChanged(list,imgs);
            }

            @Override
            public void fail() {
            }
        };
        model=new Model(context);
    }

}
