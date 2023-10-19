package com.example.videosharing.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Channel")
public class Channel implements Serializable {

    @Ignore
    public static final String CHANNEL_TITLE_KEY = "CHANNEL_TITLE";
    @Ignore
    public static final String CHANNEL_DESCRIPTION = "CHANNEL_DESCRIPTION";
    @Ignore
    public static final String CHANNEL_SUBSCRIBERS = "CHANNEL_SUBSCRIBERS";

    @PrimaryKey
    @NonNull
    private String channelTitle;
    private String channelDescription;
    private String channelSubscribers;

    public Channel(){}

    public Channel(@NonNull String channelTitle, String channelDescription, String channelSubscribers) {
        this.channelTitle = channelTitle;
        this.channelDescription = channelDescription;
        this.channelSubscribers = channelSubscribers;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public void setChannelDescription(String channelDescription) {
        this.channelDescription = channelDescription;
    }

    public void setChannelSubscribers(String channelSubscribers) {
        this.channelSubscribers = channelSubscribers;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public String getChannelDescription() {
        return channelDescription;
    }

    public String getChannelSubscribers() {
        return channelSubscribers;
    }
}
