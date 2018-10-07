package com.example.passage.model.scrouse.voice;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "voice_favorite")
public class Voice {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "voiceTitle")
    private String voiceTitle="";
    @ColumnInfo(name = "voiceAuthor")
    private String voiceAuthor;
    @ColumnInfo(name = "voicePlayer")
    private String voicePlayer;
    private String imgUrl;
    private String linkUrl;
    @ColumnInfo(name = "mp3Url")
    private String mp3Url;
    private boolean isFavorite=false;

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getMp3Url() {
        return mp3Url;
    }

    public void setMp3Url(String mp3Url) {
        this.mp3Url = mp3Url;
    }


    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getVoiceTitle() {
        return voiceTitle;
    }

    public void setVoiceTitle(@NonNull String  voiceTitle) {
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
