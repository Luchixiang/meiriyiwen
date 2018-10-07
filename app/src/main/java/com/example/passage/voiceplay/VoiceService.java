package com.example.passage.voiceplay;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class VoiceService extends Service {
    public static String path;
    public static MediaPlayer  mediaPlayer;
    private VoiceBinder voiceBinder=new VoiceBinder();

    public VoiceService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer=new MediaPlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            mediaPlayer.reset();
            path=VoiceActivity.downLoadPath+VoiceActivity.fileName;
            mediaPlayer.setDataSource(path);
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
        }catch (Exception  e)
        {
            e.printStackTrace();
        }
        mediaPlayer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        mediaPlayer=null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return voiceBinder;
    }
    public class VoiceBinder extends Binder{
        public void play()
        {
            if (!mediaPlayer.isPlaying())
            {
                mediaPlayer.start();
            }
        }
        public void pause()
        {
            if (mediaPlayer.isPlaying())
            {
                mediaPlayer.pause();
            }
        }
        public int getProgress()
        {
            return mediaPlayer.getCurrentPosition()/1000;
        }
        public int getFull()
        {
            return mediaPlayer.getDuration()/1000;
        }
        public boolean getState()
        {
            return mediaPlayer.isPlaying();
        }
        public void seek(int position)
        {
            mediaPlayer.seekTo(position);
        }
    }
}
