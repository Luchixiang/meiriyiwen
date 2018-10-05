package com.example.passage.voiceplay;

import android.Manifest;
import android.app.Application;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.passage.R;
import com.example.passage.utils.ChangeForTime;
import com.example.passage.utils.DowenLoadCompleteReceiver;
import com.example.passage.utils.FileExist;
import com.example.passage.utils.NetWorkChangeBrodcast;
import com.example.passage.voice.Voice;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class VoiceActivity extends AppCompatActivity implements VoicePlayContract.VoicePlayView {
    private String url;
    private String urlImg;
    private String voiceTitle;
    private String voiceAuthor;
    private String voicePlayer;
    private String downLoadPath;
    private IntentFilter intentFilter;
    private NetWorkChangeBrodcast workChangeBrodcast;
    private String fileName;
    private DowenLoadCompleteReceiver dowenLoadCompleteReceiver;
    private boolean isPause = false;
    private boolean isSeekbarChanging = false;
    private TextView title;
    private TextView author;
    private TextView player;
    private TextView currentTime;
    private TextView fullTime;
    private ImageView img;
    private Timer timer;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private ImageView button;
    private ImageView like;
    private Voice voice=new Voice();
    private VoicePlayContract.VoicePlayPresenter voicePlayPresenter;
    public static long downId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);
        getview();
        initView();
        voicePlayPresenter.startLoad(url);
    }

    public void getview() {
        Intent intent = getIntent();
        url = intent.getStringExtra("URL");
        voiceTitle = intent.getStringExtra("NAME");
        voiceAuthor = intent.getStringExtra("AUTHOR");
        voicePlayer = intent.getStringExtra("PLAYER");
        urlImg = intent.getStringExtra("URLIMG");
        Log.d("luchixiang", "getview: "+url);
        voice.setLinkUrl(url);
        voice.setImgUrl(urlImg);
        voice.setVoiceAuthor(voiceAuthor);
        voice.setVoiceTitle(voiceTitle);
        voice.setVoicePlayer(voicePlayer);
    }

    @Override
    public void base64(String string) {
        String mp3Url = new String(Base64.decode(string, Base64.DEFAULT));
        Log.d("luchixiang", "base64: " + mp3Url);
        voice.setMp3Url(mp3Url);
        downLoad(mp3Url);
        checkDownLoad();
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(this, s,Toast.LENGTH_LONG).show();
    }

    public void initView() {
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        workChangeBrodcast = new NetWorkChangeBrodcast();
        registerReceiver(workChangeBrodcast, intentFilter);
        title = findViewById(R.id.title_voice);
        author = findViewById(R.id.author_voice);
        currentTime = findViewById(R.id.current_time);
        fullTime = findViewById(R.id.full_time);
        player = findViewById(R.id.player_voice);
        img = findViewById(R.id.activity_image);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentTime.setText(ChangeForTime.calculateTime(mediaPlayer.getCurrentPosition() / 1000));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeekbarChanging = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress() * 1000);
                isSeekbarChanging = false;
                currentTime.setText(ChangeForTime.calculateTime(mediaPlayer.getCurrentPosition() / 1000));
            }
        });
        button = findViewById(R.id.pause);
        like=findViewById(R.id.like);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playOrPause();
            }
        });
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like.setImageResource(R.drawable.isfavorite);
                voicePlayPresenter.addVoiceFavorite(voice);
            }
        });
        Toolbar toolbar = findViewById(R.id.vocie_toolbar);
        toolbar.setTitle("播放");
        setSupportActionBar(toolbar);
        title.setText(voiceTitle);
        author.setText(voiceAuthor);
        player.setText(voicePlayer);
        VoicePlayPresenter voicePlayPresenter2 = new VoicePlayPresenter(this);
        Glide.with(this).load(urlImg).apply(new RequestOptions().fitCenter()).into(img);
    }

    //音频存在就直接播放，音频不存在则下载后播放
    public void downLoad(String downLoadUrl) {
        //动态获取写入权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(VoiceActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    23);
        }
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downLoadUrl));
        downLoadPath = Environment.getExternalStorageDirectory().getPath() + "/passage/sound/";
        fileName = voiceTitle + ".mp3";
        if (FileExist.ifFileExist(downLoadPath + fileName)) {
            //已经下载了
            initMediaPlayer();
            playOrPause();
        } else {
            //下载
            request.setDestinationInExternalPublicDir("/passage/sound/", fileName);
            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            downId = downloadManager.enqueue(request);
            Toast.makeText(this, "开始下载", Toast.LENGTH_LONG).show();
        }
    }
//初始化mediaplayer
    public void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(downLoadPath + fileName);
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int full = mediaPlayer.getDuration() / 1000;
        int current = mediaPlayer.getCurrentPosition() / 1000;
        currentTime.setText(ChangeForTime.calculateTime(current));
        fullTime.setText(ChangeForTime.calculateTime(full));
        seekBar.setMax(full);
    }
//播放或者暂停
    public void playOrPause() {
        if (mediaPlayer != null) {
            if (!isPause) {
                mediaPlayer.start();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (!isSeekbarChanging) {
                            try {
                                seekBar.setProgress(mediaPlayer.getCurrentPosition() / 1000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, 0, 50);
                button.setImageResource(R.drawable.pause);
                isPause = true;
            } else {
                mediaPlayer.pause();
                button.setImageResource(R.drawable.play);
                isPause = false;
            }
        } else {
            Toast.makeText(this, "播放失败", Toast.LENGTH_LONG).show();
        }
    }

    public void checkDownLoad() {
        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        dowenLoadCompleteReceiver = new DowenLoadCompleteReceiver();
        registerReceiver(dowenLoadCompleteReceiver, intentFilter);
    }

    @Override
    public void setPresenter(VoicePlayContract.VoicePlayPresenter presenter) {
        this.voicePlayPresenter = presenter;
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(dowenLoadCompleteReceiver);
        unregisterReceiver(workChangeBrodcast);
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        isPause = true;
    }
    @Override
    public Application gettApplication() {
        return getApplication();
    }
}
