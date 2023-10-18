package com.example.videosharing.model;


import android.util.Log;

import com.example.videosharing.YoutubeApiRequest;
import com.example.videosharing.viewModel.YoutubeApiViewModel;
import com.example.videosharing.viewModel.YoutubeViewModelMethods;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MakeYoutubeApiRequest implements Runnable {
    private YouTube ytService;
    private boolean mine;
    private String channelUserName;
    YoutubeViewModelMethods viewModelMethods;
    YoutubeApiRequest apiRequest;

    public MakeYoutubeApiRequest(GoogleAccountCredential credential,
                                 YoutubeApiViewModel viewModel,
                                 YoutubeApiRequest activity,
                                 boolean mine,
                                 String userName){
        this.apiRequest = activity;
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        ytService = new YouTube.Builder(
                transport, jsonFactory, credential
        ).setApplicationName("YouTubeAPI App")
                .build();
        viewModelMethods = viewModel;
        this.mine = mine;
        this.channelUserName = userName;
    }

    @Override
    public void run() {
        try {
            ChannelListResponse result;
            if(mine) {
                result = ytService.channels().list("snippet, contentDetails, statistics")
                        .setMine(true)
                        .execute();
                Log.d("ChannelInfo", "Got my channel info");
            } else {
                result = ytService.channels().list("snippet, contentDetails, statistics")
                        .setForUsername(channelUserName)
                        .execute();
                Log.d("ChannelInfo", "Got channel inf of " + channelUserName);
            }
            List<Channel> channels = result.getItems();
            if(channels != null){
                Channel channel = channels.get(0);
                Log.d("SET_UI", "Setting UI with Info for " + channel.getSnippet().getTitle());
                viewModelMethods.setYouTubeResponse(channel);
            }
        } catch (UserRecoverableAuthIOException e) {
            e.printStackTrace();
//            apiRequest.youTubePermissionLauncher.launch(e.getIntent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}