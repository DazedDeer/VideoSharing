package com.example.videosharing.viewModel;


import android.util.Log;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.videosharing.YoutubeApiRequest;
import com.example.videosharing.model.ChannelInfo;
import com.example.videosharing.model.MakeYoutubeApiRequest;
import com.example.videosharing.model.YoutubeDao;
import com.example.videosharing.model.YoutubeDatabase;
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

    private LiveData<List<ChannelInfo>> channels;
    private YoutubeDao ytDao;
    YoutubeApiRequest apiRequest;

    public void setApiRequestActivity(YoutubeApiRequest activity){
        this.apiRequest = activity;
        ytDao = YoutubeDatabase.getDBInstance(activity.getApplicationContext()).getDatabaseDao();
        if(channels == null){
            channels = ytDao.getChannelsInfo();
        }
    }

    public LiveData<List<ChannelInfo>> getYtChannels(){
        return channels;
    }

    public void makeYtRequest(GoogleAccountCredential credential){
        List<Thread> threads = new ArrayList<>();
        // getting information of your own YouTube channel info
        Thread thread = new Thread(new MakeYoutubeApiRequest(credential, this,
                apiRequest, true, ""));
        threads.add(thread);
        threads.get(0).start();

        String [] channels = {"GoogleDevelopers", "AndroidDevelopers", "derekbanas", "programmingwithmosh"};
        for (String name : channels) {
            Thread nThread = new Thread(new MakeYoutubeApiRequest(credential, this,
                    apiRequest, false, name));
            threads.add(nThread);
            Log.d("Thread", "Starting Thread for " + name);
            threads.get(threads.size()-1).start();
        }
    }

    @Override
    public void setYouTubeResponse(Channel channel) {
        ChannelInfo info = new ChannelInfo(
                channel.getId(),
                channel.getSnippet().getTitle(),
                channel.getStatistics().getVideoCount().toString(),
                channel.getStatistics().getViewCount().toString(),
                channel.getStatistics().getSubscriberCount().toString());
        writeToFirebase(info);
    }

    private void writeToFirebase(ChannelInfo channelInfo){
        // Connecting with the Firebase Realtime Database
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference channelCollection = db.collection("yt_channel_data");
        DocumentReference channelRef = channelCollection.document(channelInfo.getChannelId());
        channelRef.set(channelInfo)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                ytDao.insertChannelInfo(channelInfo);
                            }
                        }
                )
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("FireStoreError", "cannot add data in FireStore");
                    }
                });
    }
}