package com.example.passage.voiceplay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

public class VoiceBoadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        if (action!=null&&action.equals("playOrPause"))
        {
           if (VoiceService.mediaPlayer.isPlaying())
           {
               VoiceService.mediaPlayer.pause();
               VoiceActivity.updataNotification(false);
           }
           else {
               VoiceService.mediaPlayer.start();
               VoiceActivity.updataNotification(true);
           }
        }
    }
}
