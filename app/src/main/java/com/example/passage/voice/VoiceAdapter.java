package com.example.passage.voice;

import android.app.Activity;
import android.app.ActivityOptions;
import android.arch.persistence.room.Index;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.passage.R;
import com.example.passage.model.scrouse.voice.Voice;
import com.example.passage.tab.MainActivity;
import com.example.passage.utils.MyBitmapUtils;
import com.example.passage.voiceplay.VoiceActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class VoiceAdapter extends RecyclerView.Adapter<VoiceAdapter.ViewHolder> {
    private Context context;
    private List<Voice> mCards = new ArrayList<>();
    private RecyclerView recyclerView;
    private List<Bitmap> bitmaps = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView voiceName;
        private TextView voiceAuthor;
        private TextView voicePlayer;
        private ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            voiceName = itemView.findViewById(R.id.voice_name);
            voiceAuthor = itemView.findViewById(R.id.voice_author);
            voicePlayer = itemView.findViewById(R.id.voice_player);
            img = itemView.findViewById(R.id.voice_image);
        }
    }

    public VoiceAdapter(Context context, List<Voice> mCards, RecyclerView recyclerView) {
        this.context = context;
        this.mCards = mCards;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public VoiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final Context context1 = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context1);
        final View view = layoutInflater.inflate(R.layout.item_voice, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int positon = recyclerView.getChildAdapterPosition(v);
                Voice card = mCards.get(positon);
                String link = card.getLinkUrl();
                String imgUrl = card.getImgUrl();
                String name = card.getVoiceTitle();
                String author = card.getVoiceAuthor();
                String player = card.getVoicePlayer();
                Intent intent = new Intent(context, VoiceActivity.class);
                intent.putExtra("URL", link);
                intent.putExtra("URLIMG", imgUrl);
                intent.putExtra("NAME", name);
                intent.putExtra("AUTHOR", author);
                intent.putExtra("PLAYER", player);
                ActivityOptions activityOptions=ActivityOptions.makeSceneTransitionAnimation((Activity) context,Pair.create((View)viewHolder.img,"animation"));
                context.startActivity(intent,activityOptions.toBundle());

            }
        });
        return viewHolder;
    }

    public void ListChanged(List<Voice> list, List<Bitmap> bitmaps) {
        this.mCards = list;
        this.bitmaps = bitmaps;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!mCards.isEmpty()) {
            Voice card = mCards.get(position);
            holder.voicePlayer.setText(card.getVoicePlayer());
            holder.voiceAuthor.setText(card.getVoiceAuthor());
            holder.voiceName.setText(card.getVoiceTitle());
            Glide.with(context).load(card.getImgUrl()).apply(new RequestOptions().fitCenter()).into(holder.img);
            //new MyBitmapUtils().disPlay(holder.img,card.getImgUrl());
        }
    }

    @Override
    public int getItemCount() {
        return mCards.size();
    }
}
