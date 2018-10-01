package com.example.passage.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.passage.voice.CardComponent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Model implements ModelContract {
    private Context context;
    private String article_title="";
    private String article_main;
    private String article_author;
    private CallBack articleCallback;
    private CallBack voiceCallback;
    private List<CardComponent>mCards=new ArrayList<>();
    private List<Bitmap>imgs=new ArrayList<>();
    private int length;
    private static final String TAG="luchixiang";
    private static final int SUCCESSOFARTICLE=0x1;
    private static final int SUCCESSOFVOICE=0x2;
    public Model(Context context) {
        this.context = context;
    }
    public Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what)
            {
                case SUCCESSOFARTICLE:
                    articleCallback.successOfArticle(article_title,article_author,article_main);
                    break;
                case SUCCESSOFVOICE:
                    voiceCallback.successOfVoice(mCards,imgs);
                    break;
            }

            return false;
        }
    });

    @Override
    public void getArticle(final String url, final CallBack callBack) {
        this.articleCallback=callBack;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document= Jsoup.parse(new URL(url),100000);
                    String body=document.toString();
                    article_title=body.substring(body.indexOf("<h1>")+4,body.indexOf("</h1>"));
                   //Log.d(TAG, "run: "+article_title);
                    String body2=body.substring(body.indexOf("</h1>"));
                    article_author=body2.substring(body2.indexOf("<span>")+6,body2.indexOf("</span>"));
                    //Log.d(TAG, "run: "+article_author);
                    String body3=body2.substring(body2.indexOf("class=\"article_text\">")+21,body2.indexOf("</div>",body2.indexOf("class=\"article_text\">")));
                    String body4=body3.replaceAll("<p>","  ");
                    article_main=body4.replaceAll("</p>","\n    ")+"\n\n\n\n\n";
                    //Log.d(TAG, "run: "+article_main);
                    Message message=new Message();
                    message.what=SUCCESSOFARTICLE;
                    handler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void getVoice(final String url, CallBack callBack) {
        this.voiceCallback=callBack;
        new Thread(new Runnable() {
            @Override
            public void run() {
               try {
                   Document document=Jsoup.parse(new URL(url),100000);
                   Elements elements=document.select("div[class=list_box]");
                   length=elements.size();
                   Log.d(TAG, "run: "+length);
                   for (int i=0;i<length;i++)
                   {
                       Element element1=elements.get(i);
                       Element element2=element1.select("div[class=list_author]").first();
                       String string=element2.toString();
                       String voiceTitle=string.substring(string.indexOf("blank\">")+7,string.indexOf("</a>"));
                       //Log.d(TAG, "run: "+voiceTitle);
                        Element element3=element1.select("div[class=author_name]").first();
                        String string2=element3.toString();
                       //Log.d(TAG, "run: "+string2);
                        String voiceAuthor=string2.substring(string2.indexOf("author_name\">")+13,string2.indexOf("&nbsp;"));
                        String voicePlayer=string2.substring(string2.indexOf("主播："),string2.indexOf("</div>"));
                       //Log.d(TAG, "run: "+voiceAuthor);
                       //Log.d(TAG, "run: "+voicePlayer);
                       CardComponent cardComponent=new CardComponent();
                       cardComponent.setVoiceAuthor(voiceAuthor);
                       cardComponent.setVoicePlayer(voicePlayer);
                       cardComponent.setVoiceTitle(voiceTitle);
                       mCards.add(cardComponent);
                       Element element4=element1.select("img").first();
                       String string4=element4.toString();
                       String imgURL1=string4.substring(string4.indexOf("/upload_imgs"),string4.indexOf(">"));
                       String imgURL2="http://voice.meiriyiwen.com"+imgURL1;
                       //Log.d(TAG, "run: "+imgURL2);
                       getURLimg(imgURL2);
                   }
                   Message message=new Message();
                   message.what=SUCCESSOFVOICE;
                   handler.sendMessage(message);
               }
               catch (Exception e)
               {
                   e.printStackTrace();
               }
            }
        }).start();
    }
    public void getURLimg(final String url)
    {
        Bitmap bp;
        Log.d(TAG, "getURLimg: "+url);
        try {
            URL url1=new URL(url);
            HttpURLConnection connection=(HttpURLConnection) url1.openConnection();
            connection.setConnectTimeout(0);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            InputStream inputStream=connection.getInputStream();
            Log.d(TAG, "getURLimg: "+1);
            bp= BitmapFactory.decodeStream(inputStream);
            imgs.add(bp);
            Log.d(TAG, "getURLimg: "+imgs.size());
            inputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
