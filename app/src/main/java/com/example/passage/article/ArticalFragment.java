package com.example.passage.article;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.passage.R;

public class ArticalFragment extends Fragment implements ArticalContract.ArticleView{
    private ArticalContract.ArticlePresenter presenter;
    private ArticalPresenter articalPresenter;
    private String url="https://www.meiriyiwen.com";
    private TextView title;
    private TextView main;
    private TextView author;
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
    public void showText(String articleTitle,String articleAuthor,String articleMain) {
        title.setText(articleTitle);
        author.setText(articleAuthor);
        main.setText(articleMain);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = getActivity();
        View view=inflater.inflate(R.layout.fragment_artical,container,false);
        title=(TextView)view.findViewById(R.id.article_title);
        main=(TextView)view.findViewById(R.id.article_main);
        author=(TextView)view.findViewById(R.id.article_author);
        articalPresenter=new ArticalPresenter(this);
        presenter.startLoad(url);
        return view;
    }

    @Override
    public void setPresenter(ArticalContract.ArticlePresenter presenter) {
        this.presenter=presenter;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
