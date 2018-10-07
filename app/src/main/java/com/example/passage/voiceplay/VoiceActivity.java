package com.example.passage.voiceplay;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
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
import com.example.passage.model.scrouse.voice.Voice;

import java.util.Timer;

public class VoiceActivity extends AppCompatActivity implements VoicePlayContract.VoicePlayView {
    private String url;
    private String urlImg;
    private String voiceTitle;
    private String voiceAuthor;
    private String voicePlayer;
    public static String downLoadPath;
    public static String fileName;
    private IntentFilter intentFilter;
    private boolean isFirst = true;
    private NetWorkChangeBrodcast workChangeBrodcast;
    private DowenLoadCompleteReceiver dowenLoadCompleteReceiver;
    private boolean isPause = false;
    private TextView title;
    private TextView author;
    private TextView player;
    private TextView currentTime;
    private TextView fullTime;
    private ImageView img;
    private Timer timer;
    private SeekBar seekBar;
    private ImageView button;
    private ImageView like;
    private static RemoteViews remoteViews;
    private static Notification notification;
    private static NotificationManager notificationManager;
    private Voice voice = new Voice();
    private VoiceService.VoiceBinder voiceBinder;
    private VoicePlayContract.VoicePlayPresenter voicePlayPresenter;
    public static long downId;
    public final static String NOTIFIICATION_BUTTON="com.example.passage.button";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getview();
        initNotification();
        initView();
        voicePlayPresenter.QueryIfisLiked(voiceTitle);
        voicePlayPresenter.startLoad(url);
    }

    //从上一个activityz中获取相应信息
    public void getview() {
        Intent intent = getIntent();
        url = intent.getStringExtra("URL");
        voiceTitle = intent.getStringExtra("NAME");
        voiceAuthor = intent.getStringExtra("AUTHOR");
        voicePlayer = intent.getStringExtra("PLAYER");
        urlImg = intent.getStringExtra("URLIMG");
        voice.setLinkUrl(url);
        voice.setImgUrl(urlImg);
        voice.setVoiceAuthor(voiceAuthor);
        voice.setVoiceTitle(voiceTitle);
        voice.setVoicePlayer(voicePlayer);
    }

    //初始化activity
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
        button = findViewById(R.id.pause);
        like = findViewById(R.id.like);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPause) {
                    button.setImageResource(R.drawable.pause);
                    voiceBinder.play();
                    isPause = false;
                } else {
                    button.setImageResource(R.drawable.play);
                    voiceBinder.pause();
                    isPause = true;
                }
                updataNotification(!isPause);
            }
        });
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!voice.isFavorite()) {
                    voice.setFavorite(true);
                    voicePlayPresenter.addVoiceFavorite(voice);
                } else {
                    voicePlayPresenter.deleteVoicaeFavorite(voice);
                    voice.setFavorite(false);
                }
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
    //初始化通知栏
    public void initNotification()
    {
         notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"com.luchixiang.voiceChannel");
        notification=new Notification();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel=new NotificationChannel("com.luchixiang.voiceChannel","通知栏",NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
            channel.setSound(null,null);
        }
        remoteViews=new RemoteViews(getPackageName(),R.layout.notification);
        remoteViews.setTextViewText(R.id.notification_title,voiceTitle);
        remoteViews.setTextViewText(R.id.notification_author,voiceAuthor);
        //设置按钮得点击事件
        Intent playIintent=new Intent(this,VoiceBoadcast.class);
        playIintent.setAction("playOrPause");
        PendingIntent playPendingIntent=PendingIntent.getBroadcast(this,1,playIintent,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notification_button, playPendingIntent);
        builder.setContent(remoteViews).setSmallIcon(R.drawable.samllicon);
        builder.setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE);
        notification=builder.build();
        notification.flags=Notification.FLAG_ONGOING_EVENT;//设置通知点击或滑动时不被清除
        notificationManager.notify(102, notification);
    }
    //更新通知栏得按钮
    public static void updataNotification(boolean isPause)
    {
        if (isPause){
            remoteViews.setImageViewResource(R.id.notification_button,R.drawable.pause);
        }else {
            remoteViews.setImageViewResource(R.id.notification_button,R.drawable.play);
        }
        notificationManager.notify(102,notification);
    }
    //绑定服务
    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            voiceBinder = (VoiceService.VoiceBinder) service;
            seekBar.setMax(voiceBinder.getFull());
            fullTime.setText(ChangeForTime.calculateTime(voiceBinder.getFull()));
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        voiceBinder.seek(seekBar.getProgress() * 1000);
                    }
                    currentTime.setText(ChangeForTime.calculateTime(voiceBinder.getProgress()));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
            handler.post(runnable);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    //回调
    @Override
    public void base64(String string) {
        String mp3Url = new String(Base64.decode(string, Base64.DEFAULT));
        Log.d("luchixiang", "base64: " + mp3Url);
        voice.setMp3Url(mp3Url);
            downLoad(mp3Url);
            checkDownLoad();
            isFirst = false;
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
            Intent intent = new Intent(VoiceActivity.this, VoiceService.class);
            bindService(intent, sc, BIND_AUTO_CREATE);
            //不会从头开始放
            if (!(downLoadPath + fileName).equals(VoiceService.path)) {
                startService(intent);
            }
        } else {
            //下载
            request.setDestinationInExternalPublicDir("/passage/sound/", fileName);
            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            if (downloadManager != null)
                downId = downloadManager.enqueue(request);
            Toast.makeText(this, "开始下载", Toast.LENGTH_LONG).show();
        }
    }

    //监听下载完成
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
    public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showIslike() {
        like.setImageResource(R.drawable.isfavorite);
        voice.setFavorite(true);
    }

    @Override
    public void showDisLike() {
        like.setImageResource(R.drawable.nofavorite);
        voice.setFavorite(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(dowenLoadCompleteReceiver);
        unregisterReceiver(workChangeBrodcast);
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
        isPause = true;
        unbindService(sc);
    }

    @Override
    public Application gettApplication() {
        return getApplication();
    }

    //服务用的Handler
    public Handler handler = new Handler();
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            seekBar.setProgress(voiceBinder.getProgress());
            currentTime.setText(ChangeForTime.calculateTime(voiceBinder.getProgress()));
            seekBar.setMax(voiceBinder.getFull());
            fullTime.setText(ChangeForTime.calculateTime
                    (voiceBinder.getFull()));
            if (voiceBinder.getState()) {
                button.setImageResource(R.drawable.pause);
            } else {
                button.setImageResource(R.drawable.play);
            }
            handler.post(runnable);
        }
    };
}
