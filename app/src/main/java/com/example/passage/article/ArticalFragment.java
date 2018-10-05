package com.example.passage.article;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.passage.R;
import com.example.passage.model.scrouse.Article;
import com.example.passage.model.scrouse.ArticleCash;
import com.example.passage.utils.NetWorkUtil;

public class ArticalFragment extends Fragment implements ArticalContract.ArticleView, View.OnClickListener {
    private ArticalContract.ArticlePresenter presenter;
    private ArticalPresenter articalPresenter;
    private String url = "https://www.meiriyiwen.com";
    private String randomUrl = "https://www.meiriyiwen.com/random";
    private TextView title;
    private TextView main;
    private TextView author;
    private Article article = new Article();
    private ArticleCash articleCash=new ArticleCash();
    private ScrollView scrollView;
    private ImageView favorite;
    private ImageView next;

    public ArticalFragment() {
    }

    public static ArticalFragment newInstance() {
        ArticalFragment fragment = new ArticalFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showText(String articleTitle, String articleAuthor, String articleMain) {
        title.setText(articleTitle);
        author.setText(articleAuthor);
        main.setText(articleMain);
        article.setArticleAuthor(articleAuthor);
        article.setArticleMain(articleMain);
        article.setArticleTitle(articleTitle);
        articleCash.setArticleAuthor(articleAuthor);
        articleCash.setArticleMain(articleMain);
        articleCash.setArticleTitle(articleTitle);
        presenter.addCash(articleCash);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artical, container, false);
        initView(view);
        return view;
    }

    public void initView(View view) {
        title = (TextView) view.findViewById(R.id.article_title);
        main = (TextView) view.findViewById(R.id.article_main);
        author = (TextView) view.findViewById(R.id.article_author);
        next = view.findViewById(R.id.next);
        scrollView = view.findViewById(R.id.scv);
        favorite = view.findViewById(R.id.favorite);
        next.setOnClickListener(this);
        favorite.setOnClickListener(this);
        articalPresenter = new ArticalPresenter(this);
    }

    @Override
    public void setPresenter(ArticalContract.ArticlePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showwToast(String string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                if (NetWorkUtil.isNetworkAvailable(getActivity()))
                {presenter.startLoad(randomUrl);
                scrollView.fullScroll(View.FOCUS_UP);
                //要在这里判断是否喜欢
                favorite.setImageResource(R.drawable.nofavorite);}
                else {
                    Toast.makeText(getActivity(),"请检查网络状况",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.favorite:
                article.setFavorited(true);
                presenter.addFavorite(article);
                favorite.setImageResource(R.drawable.isfavorite);
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("luchixiang", "onStop: " + 1);
    }

    @Override
    public Application getApplication() {
        return getActivity().getApplication();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (NetWorkUtil.isNetworkAvailable(getActivity())) {
            presenter.startLoad(url);
        } else {
            presenter.getCash();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("luchixiang", "onDestroy: " + 1);
        presenter.onDestory();
        presenter = null;
        articalPresenter = null;
    }
}
