package com.example.passage.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class AudioPlayerUtils {
    private MediaPlayer mediaPlayer;
    public static int palyStatus = -1;
    private volatile static AudioPlayerUtils audioPlayerUtils = null;
    File tempFile = null;
    private static final String TAG = "luchixiang";

    private AudioPlayerUtils() {
    }

    public static AudioPlayerUtils getInstance() {
        if (audioPlayerUtils == null) {
            synchronized (AudioPlayerUtils.class) {
                if (audioPlayerUtils == null) {
                    audioPlayerUtils = new AudioPlayerUtils();
                }
            }
        }
        audioPlayerUtils.initView();
        return audioPlayerUtils;
    }

    public void initView() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
    }

    public void playBase64(final Context context, String base64Str) {
        try {
            tempFile = base64ToFile(base64Str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (palyStatus) {
            case 1:
                mediaPlayer.stop();
                mediaPlayer.reset();
                break;
            case 0:
                mediaPlayer.reset();
                break;
            case -1:
                break;
        }
        palyStatus = 1;
        try {
            /*File file=new File(tempFile.getPath());
            FileInputStream fis=new FileInputStream(file);
            mediaPlayer.reset();*/
            mediaPlayer.setDataSource(tempFile.getPath());
            Log.d(TAG, "playBase64: " + tempFile.getPath());
            mediaPlayer.setLooping(false);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.d(TAG, "onPrepared: ");
                    mediaPlayer.start();
                }
            });
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer.reset();
                    Log.d(TAG, "onCompletion: ");
                    palyStatus = 0;
                    if (tempFile != null && tempFile.exists()) {
                        tempFile.delete();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File base64ToFile(String base64Str) {
        FileOutputStream outputStream = null;
        if (tempFile == null || !tempFile.exists()) try {
            tempFile = File.createTempFile("temp", ".mp3");
            byte[] audioByte = Base64.decode(base64Str, Base64.DEFAULT);
            if (tempFile != null) {
                outputStream = new FileOutputStream(tempFile);
                outputStream.write(audioByte, 0, audioByte.length);
                outputStream.flush();
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tempFile;
    }

    public void stop() {
        if (palyStatus == 1 && mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}
