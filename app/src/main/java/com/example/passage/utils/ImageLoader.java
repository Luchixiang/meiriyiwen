/*package com.example.passage.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.example.passage.R;

import java.security.PublicKey;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import libcore.io.DiskLruCache;

public class ImageLoader {
    public static String TAG="luchixiang"
    public static int MESSAGE_POST_RESULT=1;
    private static final int CPU_COUNT=Runtime.getRuntime().availableProcessors();
    private static final  int CORE_POOL_SIZE=CPU_COUNT+1;
    private static final int MAXIMUN_PPOL_SIZE=CPU_COUNT*2+1;
    private static final long KEEP_ALIVE=10L;

    private static final int TAG_KEY_URL= R.id.imageloader_url;
    private static final long DISK_CACHE_SIZE=1024*1024*50;
    private static final int IO_BUFFER_SIZE=8*1024;
    private static final int DISK_CACHE_INDEX=0;
    private boolean mISsDiskLruCacheCreated=false;

    private static final ThreadFactory sThreadFactory=new ThreadFactory() {
        private final AtomicInteger mCount=new AtomicInteger();
        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r,"ImageLoader#"+mCount.getAndIncrement());
        }
    };

    public static final Executor THREAD_POOL_EXECUTOR =new ThreadPoolExecutor(CORE_POOL_SIZE,MAXIMUN_PPOL_SIZE,KEEP_ALIVE, TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>(),sThreadFactory);

    private Handler mMainHandler=new Handler(Looper.getMainLooper())
    {
        @Override
        public void handleMessage(Message msg) {
            LoaderResult result=(LoaderResult) msg.obj;
            ImageView imageView=result.imagView;
            String uri=(String) imageView.getTag(TAG_KEY_URL);
            if (uri.equals(result.uri))
            {
                imageView.setImageBitmap(result.bitmap);
            }
            else {
                Log.d(TAG, "handleMessage: ");
            }
        }
    };
    private Context mContext;
    private ImageResizer imageResizer=new ImageResizer();
    private LruCache<String,Bitmap> mMemoryCache;
    private DiskLruCache mDisLruCache;
    private ImageLoader(Context context)
    {
        mContext=context.getApplicationContext();
        int maxMemory=(int)(Runtime.getRuntime().maxMemory()/1024);
        int cache
    }
}*/
