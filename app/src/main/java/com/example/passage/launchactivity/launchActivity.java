package com.example.passage.launchactivity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.passage.R;
import com.example.passage.tab.MainActivity;
import com.example.passage.utils.NetWorkUtil;

public class launchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
    }

    @Override
    protected void onStart() {
        Log.i("MainActivity", "onStart");
        //动态获取存储权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    23);
        }
        if (!NetWorkUtil.isNetworkAvailable(this)) {
            showSetNetworkUI(this);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        super.onStart();
    }


    public void showSetNetworkUI(final Context context) {
// 提示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("网络设置提示")
                .setMessage("网络连接不可用,是否进行设置?")
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override

                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = null;
// 判断手机系统的版本 即API大于10 就是3.0或以上版本
                        if (android.os.Build.VERSION.SDK_INT > 10) {
                            intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
                        } else {
                            intent = new Intent();
                            ComponentName component = new ComponentName("com.android.settings",
                                    "com.android.settings.WirelessSettings");
                            intent.setComponent(component);
                            intent.setAction("android.intent.action.VIEW");
                        }
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Toast.makeText(context, "获取网络失败", Toast.LENGTH_LONG).show();
                    }
                }).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
