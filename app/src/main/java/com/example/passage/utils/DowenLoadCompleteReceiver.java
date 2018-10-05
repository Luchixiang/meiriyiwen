package com.example.passage.utils;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import com.example.passage.voiceplay.VoiceActivity;

public class DowenLoadCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        long downLoadCompleteID=intent.getLongExtra(DownloadManager.ACTION_DOWNLOAD_COMPLETE,-1);
        if (downLoadCompleteID== VoiceActivity.downId)
        {
            Toast.makeText(context,"下载完成",Toast.LENGTH_LONG).show();
        }
    }
}
