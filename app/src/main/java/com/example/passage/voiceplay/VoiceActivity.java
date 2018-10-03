package com.example.passage.voiceplay;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.passage.R;
import com.example.passage.utils.AudioPlayerUtils;

import java.io.IOException;

public class VoiceActivity extends AppCompatActivity implements VoicePlayContract.VoicePlayView{
    private String url;
    private String urlImg;
    private String voiceTitle;
    private String voiceAuthor;
    private String voicePlayer;
    private TextView title;
    private TextView author;
    private TextView player;
    private ImageView img;
    private SeekBar seekBar;
    private FloatingActionButton pauseButton;
    private VoicePlayContract.VoicePlayPresenter voicePlayPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);
        getview();
        initView();
        voicePlayPresenter.startLoad(url);
    }
    public void getview()
    {
        Intent intent=getIntent();
        url=intent.getStringExtra("URL");
        voiceTitle=intent.getStringExtra("NAME");
        voiceAuthor=intent.getStringExtra("AUTHOR");
        voicePlayer=intent.getStringExtra("PLAYER");
        urlImg=intent.getStringExtra("URLIMG");
    }

    @Override
    public void base64(String string) {
        //AudioPlayerUtils.getInstance().playBase64(this,string);
        Log.d("luchixiang", "base64: "+new String(Base64.decode(string,Base64.DEFAULT)));
        final MediaPlayer mediaPlayer=new MediaPlayer();
        try {
            mediaPlayer.setDataSource(new String(Base64.decode(string,Base64.DEFAULT)));
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                }
            });
            mediaPlayer.prepareAsync();
        }catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public void initView()
    {
        title=findViewById(R.id.title_voice);
        author=findViewById(R.id.author_voice);
        player=findViewById(R.id.player_voice);
        img=findViewById(R.id.activity_image);
        seekBar=findViewById(R.id.seekBar);
        pauseButton=findViewById(R.id.pause);
        Toolbar toolbar=findViewById(R.id.vocie_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("播放");
        title.setText(voiceTitle);
        author.setText(voiceAuthor);
        player.setText(voicePlayer);
        VoicePlayPresenter voicePlayPresenter2=new VoicePlayPresenter(this);
        Glide.with(this).load(urlImg).apply(new RequestOptions().fitCenter()).into(img);
    }

    @Override
    public void setPresenter(VoicePlayContract.VoicePlayPresenter presenter) {
        this.voicePlayPresenter=presenter;
    }
}
