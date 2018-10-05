package com.example.passage.favoriteArticle;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.passage.R;
import com.example.passage.model.scrouse.Article;

import java.util.ArrayList;
import java.util.List;

public class FavtoriteArticleFragment extends Fragment implements FavoriteContract.FavoriteView{
    private RecyclerView recyclerView;
    private List<Article> favoriteArticles = new ArrayList<>();
    private FavoriteAdapter favoriteAdapter;
    private FavoriteContract.FavoritePresenter presenter;
    public static FavtoriteArticleFragment newInstance() {
        FavtoriteArticleFragment fragment = new FavtoriteArticleFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showwToast(String string) {
        Toast.makeText(getActivity(),string,Toast.LENGTH_LONG).show();
    }

    @Override
    public Application gettApplication() {
        return getActivity().getApplication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shelf, container, false);
        recyclerView=view.findViewById(R.id.favorite_artiles);
        Context context = getActivity();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        favoriteAdapter = new FavoriteAdapter(context, favoriteArticles, recyclerView);
        recyclerView.setAdapter(favoriteAdapter);
        favoriteAdapter.notifyDataSetChanged();
        FavoritePresenter favoritePresenter=new FavoritePresenter(this);
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.getFavorite();
    }

    @Override
    public void showArticle(List<Article> articles) {
        favoriteAdapter.ListChanged(articles);
        favoriteAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(FavoriteContract.FavoritePresenter presenter) {
        this.presenter=presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestory();
        presenter=null;
    }
}
