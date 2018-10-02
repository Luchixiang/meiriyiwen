package com.example.passage.voice;

import android.widget.ImageView;
import android.widget.TextView;

public class CardComponent {
    private ImageView imageView;
    private String voiceTitle;
    private String voiceAuthor;
    private String voicePlayer;

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public String getVoiceTitle() {
        return voiceTitle;
    }

    public void setVoiceTitle(String voiceTitle) {
        this.voiceTitle = voiceTitle;
    }

    public String getVoiceAuthor() {
        return voiceAuthor;
    }

    public void setVoiceAuthor(String voiceAuthor) {
        this.voiceAuthor = voiceAuthor;
    }

    public String getVoicePlayer() {
        return voicePlayer;
    }

    public void setVoicePlayer(String voicePlayer) {
        this.voicePlayer = voicePlayer;
    }
}
