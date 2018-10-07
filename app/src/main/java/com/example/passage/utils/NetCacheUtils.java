package com.example.passage.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetCacheUtils {
    private LocalCacheUtils mLocalCacheUtils;
    private MemoryCacheUtils mMemoryCacheUtils;

    public NetCacheUtils(LocalCacheUtils localCacheUtils, MemoryCacheUtils memoryCacheUtils) {
        mLocalCacheUtils = localCacheUtils;
        mMemoryCacheUtils = memoryCacheUtils;
    }

    public void getBitmapFromNet(ImageView ivPic, String url) {
        new BitmapTask().execute(ivPic, url);//启动AsyncTask

    }

    class BitmapTask extends AsyncTask<Object, Void, Bitmap> {

        private ImageView ivPic;
        private String url;

        @Override
        protected Bitmap doInBackground(Object[] params) {
            ivPic = (ImageView) params[0];
            url = (String) params[1];

            return downLoadBitmap(url);
        }

        @Override
        protected void onProgressUpdate(Void[] values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                ivPic.setImageBitmap(result);

                //从网络获取图片后,保存至本地缓存
                mLocalCacheUtils.setBitmapToLocal(url, result);
                //保存至内存中
                mMemoryCacheUtils.setBitmapToMemory(url, result);

            }
        }
        private Bitmap downLoadBitmap(String url) {
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) new URL(url).openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                conn.setRequestMethod("GET");

                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    //图片压缩
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    //options.inSampleSize=2;//宽高压缩为原来的1/2
                    options.inPreferredConfig=Bitmap.Config.ARGB_4444;
                    Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream(),null,options);
                    //将图片放大
                    int width=bitmap.getWidth();
                    int height=bitmap.getHeight();
                    int trueWidth=ivPic.getWidth();
                    int trueHeight=ivPic.getHeight();
                    Matrix matrix=new Matrix();
                    matrix.postScale(5,5);
                    Bitmap newBitmap=Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);
                    return newBitmap;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                assert conn!=null;
                conn.disconnect();
            }

            return null;
        }
    }

}
