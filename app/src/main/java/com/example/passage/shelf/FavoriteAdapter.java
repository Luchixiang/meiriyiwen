package com.example.passage.shelf;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.passage.R;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private Context context;
    private List<Article> favoriteAricle = new ArrayList<>();
    private RecyclerView recyclerView;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView favoriteTitle;
        TextView favoriteAuthor;
        TextView favoriteMain;

        public ViewHolder(View view) {
            super(view);
            favoriteTitle = view.findViewById(R.id.favorite_title);
            favoriteAuthor = view.findViewById(R.id.favorite_author);
            favoriteMain = view.findViewById(R.id.favorite_main);
        }
    }

    public FavoriteAdapter(Context context, List<Article> favoriteAricle, RecyclerView recyclerView) {
        this.context = context;
        this.favoriteAricle = favoriteAricle;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!favoriteAricle.isEmpty()) {
            Article article = favoriteAricle.get(position);
            holder.favoriteTitle.setText(article.getArticleTitle());
            holder.favoriteAuthor.setText(article.getArticleAuthor());
            holder.favoriteMain.setText(article.getArticleMain());
        }
    }
    public void ListChanged(List<Article>articles)
    {
        this.favoriteAricle=articles;
    }
    @Override
    public int getItemCount() {
        return favoriteAricle.size();
    }
}
