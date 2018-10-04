package com.example.passage.voice;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.passage.R;

import java.util.ArrayList;
import java.util.List;

public class VoiceFragment extends Fragment implements VoiceContract.VoiceView {

    private VoiceContract.VoicePresenter voicePresenter;
    private String url = "http://voice.meiriyiwen.com/";
    private VoiceAdapter voiceAdapter;
    private List<CardComponent> mCards = new ArrayList<>();
    private RecyclerView recyclerView;

    public static VoiceFragment newInstance() {
        VoiceFragment fragment = new VoiceFragment();
        return fragment;
    }

    @Override
    public void setPresenter(VoiceContract.VoicePresenter presenter) {
        this.voicePresenter = presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void listChanged(List<CardComponent> list, List<Bitmap> bitmaps) {
        voiceAdapter.ListChanged(list, bitmaps);
        Log.d("luchixiang", "listChanged: " + bitmaps.size());
        voiceAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = getActivity();
        View view = inflater.inflate(R.layout.fragment_voice, container, false);
        recyclerView = view.findViewById(R.id.show_voice);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        voiceAdapter = new VoiceAdapter(context, mCards, recyclerView);
        recyclerView.setAdapter(voiceAdapter);
        voiceAdapter.notifyDataSetChanged();
        PresenterOfVoice presenter = new PresenterOfVoice(this);
        voicePresenter.startLoad(url);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        voicePresenter.onDestory();
        voicePresenter=null;

    }
}
