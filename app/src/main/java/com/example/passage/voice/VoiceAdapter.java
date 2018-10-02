package com.example.passage.voice;

import android.content.Context;
import android.graphics.Bitmap;
import android.speech.tts.Voice;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.passage.R;

import java.util.ArrayList;
import java.util.List;

public class VoiceAdapter extends RecyclerView.Adapter<VoiceAdapter.ViewHolder> {
    private Context context;
    private List<CardComponent> mCards = new ArrayList<>();
    private RecyclerView recyclerView;
    private List<Bitmap>bitmaps=new ArrayList<>();

    //public View.OnClickListener mLisenstener=new mListener();
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

    public VoiceAdapter(Context context, List<CardComponent> mCards, RecyclerView recyclerView) {
        this.context = context;
        this.mCards = mCards;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public VoiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context1 = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context1);
        View view = layoutInflater.inflate(R.layout.item_voice, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        //舰艇动作
        return viewHolder;
    }
    public void ListChanged(List<CardComponent> list, List<Bitmap>bitmaps)
    {
        this.mCards=list;
        this.bitmaps=bitmaps;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!mCards.isEmpty()) {
            CardComponent card = mCards.get(position);
            Bitmap bitmap=bitmaps.get(position);
            holder.voicePlayer.setText(card.getVoicePlayer());
            holder.voiceAuthor.setText(card.getVoiceAuthor());
            holder.voiceName.setText(card.getVoiceTitle());
            holder.img.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return mCards.size();
    }
}
