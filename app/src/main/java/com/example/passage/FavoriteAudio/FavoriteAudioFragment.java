package com.example.passage.FavoriteAudio;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.passage.R;
import com.example.passage.model.scrouse.voice.Voice;
import com.example.passage.voice.VoiceAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAudioFragment extends Fragment implements FavoriteAudioContract.FavoriteAudioView{
    private FavoriteAudioContract.FavoriteAudioPresenter presenter;
    private RecyclerView recyclerView;
    private VoiceAdapter adapter;
    private LinearLayoutManager layoutManager;
    private int lastposition;
    private int lastOffset;
    private List<Bitmap>bitmaps=new ArrayList<>();
    private List<Voice>cards=new ArrayList<>();
    public static FavoriteAudioFragment newInstance() {
        return new FavoriteAudioFragment();
    }

    @Override
    public void showAudio(List<Voice> cards) {
        adapter.ListChanged(cards,bitmaps);
        adapter.notifyDataSetChanged();
    }

    @Override
    public Application gettApplication() {
        if (getActivity()!=null)
        {
            return getActivity().getApplication();
        }
        return null;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_favorite_audio, container, false);
        initView(view);
        return view;
    }
    public void initView(View view) {
        recyclerView = view.findViewById(R.id.favorite_audio);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                View topView=layoutManager.getChildAt(0);
                lastOffset=topView.getTop();
                lastposition=layoutManager.getPosition(topView);
            }
        });
        adapter = new VoiceAdapter(getActivity(), cards, recyclerView);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        FavoriteAudioPresenter audioPresenter = new FavoriteAudioPresenter(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.getFavoriteAudio();
        layoutManager.scrollToPosition(lastposition);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestory();
        presenter=null;
    }
}
