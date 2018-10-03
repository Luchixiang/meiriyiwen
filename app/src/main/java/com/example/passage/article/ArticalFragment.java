package com.example.passage.article;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.passage.R;

public class ArticalFragment extends Fragment implements ArticalContract.ArticleView, View.OnClickListener {
    private ArticalContract.ArticlePresenter presenter;
    private ArticalPresenter articalPresenter;
    private String url = "https://www.meiriyiwen.com/random";
    private String randomUrl = "https://www.meiriyiwen.com/random";
    private TextView title;
    private TextView main;
    private TextView author;
    private FloatingActionButton favorite;
    private FloatingActionButton next;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = getActivity();
        View view = inflater.inflate(R.layout.fragment_artical, container, false);
        initView(view);
        presenter.startLoad(url);
        return view;
    }

    public void initView(View view) {
        title = (TextView) view.findViewById(R.id.article_title);
        main = (TextView) view.findViewById(R.id.article_main);
        author = (TextView) view.findViewById(R.id.article_author);
        next = view.findViewById(R.id.next);
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
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                presenter.startLoad(randomUrl);
                break;
            case R.id.favorite:
                break;
        }
    }
}
