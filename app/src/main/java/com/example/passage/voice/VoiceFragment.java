package com.example.passage.voice;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.passage.R;
import com.example.passage.model.scrouse.voice.Voice;

import java.util.ArrayList;
import java.util.List;

public class VoiceFragment extends Fragment implements VoiceContract.VoiceView {

    private VoiceContract.VoicePresenter voicePresenter;
    private String url = "http://voice.meiriyiwen.com/";
    private VoiceAdapter voiceAdapter;
    private List<Voice> mCards = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private int lastposition;
    private int lastOffset;

    public static VoiceFragment newInstance() {
        return new VoiceFragment();
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
    public void listChanged(List<Voice> list, List<Bitmap> bitmaps) {
        voiceAdapter.ListChanged(list, bitmaps);
        Log.d("luchixiang", "listChanged: " + bitmaps.size());
        voiceAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voice, container, false);
        initView(view);
        return view;
    }
    public void initView(View view)
    {
        Context context = getActivity();
        recyclerView = view.findViewById(R.id.show_voice);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        //设置滚动回来是上一次最后游览得位置
        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                View topView=layoutManager.getChildAt(0);
                lastOffset=topView.getTop();
                lastposition=layoutManager.getPosition(topView);
            }
        });
        voiceAdapter = new VoiceAdapter(context, mCards, recyclerView);
        recyclerView.setAdapter(voiceAdapter);
        voiceAdapter.notifyDataSetChanged();
    }
    @Override
    public void onStart() {
        super.onStart();
        layoutManager.scrollToPosition(lastposition);
        PresenterOfVoice presenter = new PresenterOfVoice(this);
        voicePresenter.startLoad(url);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        voicePresenter.onDestory();
        voicePresenter=null;

    }
}
