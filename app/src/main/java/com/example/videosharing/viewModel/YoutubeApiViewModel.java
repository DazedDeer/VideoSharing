package com.example.videosharing.viewModel;


import android.util.Log;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.videosharing.YoutubeApiRequest;
import com.example.videosharing.channelModel.Ittems;
import com.example.videosharing.model.ChannelDao;
import com.example.videosharing.model.ChannelDatabase;
import com.example.videosharing.model.ChannelInfo;
import com.example.videosharing.model.MakeYoutubeApiRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.services.youtube.model.Channel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;



public class YoutubeApiViewModel extends ViewModel implements YoutubeViewModelMethods{

    private LiveData<List<com.example.videosharing.model.Channel>> channels;
    private ChannelDao channelDao;
    YoutubeApiRequest apiRequest;

    public void setApiRequestActivity(YoutubeApiRequest activity){
        this.apiRequest = activity;
        channelDao = ChannelDatabase.getChanDBInstance(activity.getApplicationContext()).getChannelDBDao();
        if(channels == null){
            channels = channelDao.getChannels();
        }
    }

    public LiveData<List<com.example.videosharing.model.Channel>> getYtChannels(){
        return channels;
    }


    @Override
    public void setYouTubeResponse(Channel channel) {

    }
}