package com.example.passage.model;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.passage.model.scrouse.ArticleCash;
import com.example.passage.model.scrouse.ArticleRespority;
import com.example.passage.model.scrouse.Article;
import com.example.passage.voice.Voice;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Model implements ModelContract {
    private Context context;
    private String articleTitle = "";
    private String articleMain;
    private String articleAuthor;
    private ArticleCallBack articleCallback;
    private VoiceCallBack voiceCallback;
    private VoicePlayCallBack voicePlayCallBack;
    private FavoriteCallBack favoriteCallBack;
    private cashCallBack cashCallBack;
    private List<Article> favoriteArticles=new ArrayList<>();
    private List<Voice> mCards = new ArrayList<>();
    private List<Bitmap> imgs = new ArrayList<>();
    private ArticleRespority articleRespority;
    private int length;
    private String swfUrl;
    private static final String TAG = "luchixiang";
    private static final int SUCCESSOFARTICLE = 0x1;
    private static final int SUCCESSOFVOICE = 0x2;
    private static final int SUCCESSOFVOICEPLAYER = 0x3;
    private MyHandler myHandler = new MyHandler(this);

    public Model() {

    }

    @Override
    public void getArticle(final String url, final ArticleCallBack callBack) {
        this.articleCallback = callBack;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.parse(new URL(url), 100000);
                    Element element = document.getElementById("article_show");
                    Elements elements = document.getElementsByClass("article_text")
                            .get(0).select("p");
                    articleTitle = element.getElementsByTag("h1").get(0).text();
                    articleAuthor = element.getElementsByClass("article_author").get(0)
                            .getElementsByTag("p").get(0).text();

                    StringBuilder articleBuilder = new StringBuilder();
                    for (Element e : elements) {
                        if (!e.text().isEmpty()){
                        articleBuilder.append("\n").append(e.text()+"  ");}
                    }
                    articleMain = articleBuilder.toString();
                    Message message = new Message();
                    message.what = SUCCESSOFARTICLE;
                    myHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void getVoice(final String url, VoiceCallBack callBack) {
        this.voiceCallback = callBack;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.parse(new URL(url), 100000);
                    Elements elements = document.select("div[class=list_box]");
                    length = elements.size();
                    Log.d(TAG, "run: " + length);
                    for (int i = 0; i < length; i++) {
                        Element element1 = elements.get(i);
                        Element element2 = element1.select("div[class=list_author]").first();
                        String string = element2.toString();
                        String voiceTitle = string.substring(string.indexOf("blank\">") + 7, string.indexOf("</a>"));
                        Element element3 = element1.select("div[class=author_name]").first();
                        String string2 = element3.toString();
                        String voiceAuthor = string2.substring(string2.indexOf("author_name\">") + 13, string2.indexOf("&nbsp;"));
                        String voicePlayer = "\n" + "   " + string2.substring(string2.indexOf("主播："), string2.indexOf("</div>"));
                        Voice cardComponent=new Voice();
                        cardComponent.setVoiceAuthor(voiceAuthor);
                        cardComponent.setVoicePlayer(voicePlayer);
                        cardComponent.setVoiceTitle(voiceTitle);
                        String imgUrl = element1.select(".box_list_img").select("img")
                                .attr("abs:src");
                        cardComponent.setImgUrl(imgUrl);
                        String linkUrl = element1.select(".box_list_img").attr("abs:href");
                        cardComponent.setLinkUrl(linkUrl);
                        mCards.add(cardComponent);
                    }
                    Message message = new Message();
                    message.what = SUCCESSOFVOICE;
                    myHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /*public void getURLimg(final String url)
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
            bp= BitmapFactory.decodeStream(inputStream,null,new BitmapFactory.Options());
            imgs.add(bp);
            Log.d(TAG, "getURLimg: "+imgs.size());
            inputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }*/
    private static class MyHandler extends Handler {
        private final WeakReference<Model> mModel;

        public MyHandler(Model model) {
            mModel = new WeakReference<>(model);
        }

        @Override
        public void handleMessage(Message msg) {
            Model model = mModel.get();
            super.handleMessage(msg);
            if (model != null) {
                switch (msg.what) {
                    case SUCCESSOFARTICLE:
                        model.articleCallback.successOfArticle(model.articleTitle, model.articleAuthor, model.articleMain);
                        break;
                    case SUCCESSOFVOICE:
                        model.voiceCallback.successOfVoice(model.mCards, model.imgs);
                        break;
                    case SUCCESSOFVOICEPLAYER:
                        model.voicePlayCallBack.suceessOfVoicePlay(model.swfUrl);
                }
            }
        }
    }

    public void getVoicePlay(final String url, VoicePlayCallBack voicePlayCallBack) {
        this.voicePlayCallBack = voicePlayCallBack;
        //Log.d(TAG, "getVoicePlay: "+url);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.parse(new URL(url), 60000);
                    Element element = document.select("p[class=p_file]").first();
                    String string = element.select("embed").attr("abs:src");
                    String rex = "=(.*?)&";
                    Pattern pattern = Pattern.compile(rex);//匹配规则
                    Matcher matcher = pattern.matcher(string);
                    matcher.find();
                    swfUrl = matcher.group(1);
                    Message message = new Message();
                    message.what = SUCCESSOFVOICEPLAYER;
                    myHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void getCash(final cashCallBack cashCallBack,Application application)
    {
        this.cashCallBack=cashCallBack;
        articleRespority=ArticleRespority.getINSTANCE(application);
        articleRespority.getArticleCash(new ArticleRespority.resporityCallback() {
            @Override
            public void favoriteCallBack(List<Article> articles) {
            }

            @Override
            public void cashCallBack(List<ArticleCash> articleCashes) {
                cashCallBack.successOfGetCash(articleCashes);
                Log.d(TAG, "cashCallBack: "+articleCashes.get(0).getArticleTitle());
            }
        });
    }
    public void addCash(Application application,ArticleCash articleCash)
    {
        articleRespority=ArticleRespority.getINSTANCE(application);
        articleRespority.insertCash(articleCash);
        Log.d(TAG, "addCash: "+articleCash.getArticleTitle());
    }
    public void getFavorite(final FavoriteCallBack favoriteCallBack, Application application)
    {
        this.favoriteCallBack=favoriteCallBack;
        articleRespority=ArticleRespority.getINSTANCE(application);
        articleRespority.getFavorites(new ArticleRespority.resporityCallback() {
            @Override
            public void favoriteCallBack(List<Article> favoriteArticles) {
                favoriteCallBack.successOfGetFavorite(favoriteArticles);
                //Log.d(TAG, "favoriteCallBack: "+favoriteArticles.get(0).getArticleTitle());
            }
            @Override
            public void cashCallBack(List<ArticleCash> articleCashes) {
            }
        });
    }
    public void addFavorite(ArticleCallBack articleCallBack,Article article,Application application)
    {
        this.articleCallback=articleCallBack;
        articleRespority=ArticleRespority.getINSTANCE(application);
        articleRespority.insertFavorite(article);
        Log.d(TAG, "addFavorite: "+article.getArticleTitle());
        articleCallBack.successOfAddFavorite();
    }

    @Override
    public void cancelTask() {
        articleRespority.deleteTasks();
    }
    public void getFavoriteAudio(final FavoriteAudioCallBack favoriteAudioCallBack, Application application)
    {
        articleRespority=ArticleRespority.getINSTANCE(application);
        articleRespority.getVoiceFavorite(new ArticleRespority.resorityVoiceCallback() {
            @Override
            public void getVoiceCallBack(List<Voice> voices) {
                favoriteAudioCallBack.successofGetAudioFavorite(voices);
            }
        });
    }
    public void addVoiceFavorite(VoicePlayCallBack voicePlayCallBack,Voice voice,Application application)
    {
        this.voicePlayCallBack=voicePlayCallBack;
        articleRespority=ArticleRespority.getINSTANCE(application);
        articleRespority.insertVoiceFavorite(voice);
        voicePlayCallBack.successOfAddVoicae("收藏成功");
    }
}
