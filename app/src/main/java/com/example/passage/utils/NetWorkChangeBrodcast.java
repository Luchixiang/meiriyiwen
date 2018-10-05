package com.example.passage.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NetWorkChangeBrodcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!NetWorkUtil.isNetworkAvailable(context))
        {
            Toast.makeText(context,"当前网络不可用",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(context,"当前网络可用",Toast.LENGTH_LONG).show();
        }
    }
}
