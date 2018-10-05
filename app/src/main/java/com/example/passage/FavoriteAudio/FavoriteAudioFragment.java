package com.example.passage.FavoriteAudio;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.passage.R;
import com.example.passage.voice.Voice;
import com.example.passage.voice.VoiceAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAudioFragment extends Fragment implements FavoriteAudioContract.FavoriteAudioView{
    private FavoriteAudioContract.FavoriteAudioPresenter presenter;
    private RecyclerView recyclerView;
    private VoiceAdapter adapter;
    private List<Bitmap>bitmaps=new ArrayList<>();
    private List<Voice>cards=new ArrayList<>();
    public static FavoriteAudioFragment newInstance() {
        FavoriteAudioFragment fragment = new FavoriteAudioFragment();
        return fragment;
    }

    @Override
    public void showAudio(List<Voice> cards) {
        adapter.ListChanged(cards,bitmaps);
        adapter.notifyDataSetChanged();
    }

    @Override
    public Application gettApplication() {
        return getActivity().getApplication();
    }

    @Override
    public void showwToast(String string) {

    }

    @Override
    public void setPresenter(FavoriteAudioContract.FavoriteAudioPresenter presenter) {
        this.presenter=presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_favorite_audio, container, false);
        initView(view);
        return view;
    }
    public void initView(View view) {
        recyclerView = view.findViewById(R.id.favorite_audio);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new VoiceAdapter(getActivity(), cards, recyclerView);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        FavoriteAudioPresenter audioPresenter = new FavoriteAudioPresenter(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.getFavoriteAudio();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestory();
        presenter=null;
    }
}
