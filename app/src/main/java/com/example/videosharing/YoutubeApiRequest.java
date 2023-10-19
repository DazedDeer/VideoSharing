package com.example.videosharing;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.videosharing.API.SingltonRetrofitObject;
import com.example.videosharing.channelModel.ChannelVideoModel;
import com.example.videosharing.model.Channel;
import com.example.videosharing.model.ChannelInfo;
import com.example.videosharing.model.Items;
import com.example.videosharing.channelModel.Ittems;
import com.example.videosharing.model.VideoModel;
import com.example.videosharing.viewModel.YoutubeApiViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YoutubeApiRequest extends AppCompatActivity {

    String astley_url;
    RecyclerView recyclerView, channelRecyclerView;
    ProgressBar progressBar, chanProgressBar;


    TextView channelName, channelDesc, subscribers;
    YoutubeApiViewModel ytViewModel;
    private DatabaseReference mDatabase;
    DatabaseReference myRef;
    FirebaseDatabase database;
    GoogleSignInAccount account;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_api_request);

        recyclerView = findViewById(R.id.recyclerView);
        //channelRecyclerView = findViewById(R.id.recyclerViewChan);
        progressBar = findViewById(R.id.progressBar);
        //chanProgressBar = findViewById(R.id.progressBarChan);
        channelName = findViewById(R.id.channel_name);
        channelDesc = findViewById(R.id.channel_desc);
        subscribers = findViewById(R.id.subscribers);
        back = findViewById(R.id.backChannelBtn);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();



        ytViewModel = new ViewModelProvider(this).get(YoutubeApiViewModel.class);
        ytViewModel.setApiRequestActivity(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("astley");
        // Read from the database


        ValueEventListener titlePostListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                channelName.setText(String.valueOf(dataSnapshot.getValue()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("firebase", "title firebase read failed", databaseError.toException());
            }
        };
        myRef.child("Channel").child("Title").addValueEventListener(titlePostListener);

        ValueEventListener descriptionPostListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                channelDesc.setText(String.valueOf(dataSnapshot.getValue()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("firebase", "description firebase read failed", databaseError.toException());
            }
        };
        myRef.child("Channel").child("Description").addValueEventListener(descriptionPostListener);

        ValueEventListener subscribersPostListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                subscribers.setText(String.valueOf(dataSnapshot.getValue()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("firebase", "subscribers firebase read failed", databaseError.toException());
            }
        };
        myRef.child("Channel").child("Subscribers").addValueEventListener(subscribersPostListener);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main);
            }
        });

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        account = GoogleSignIn.getLastSignedInAccount(this);
        if (account == null) {
            Intent login = new Intent(getApplicationContext(), Login.class);
            startActivity(login);
        }

        doApiCall();
    }

    //https://www.googleapis.com/youtube/v3/search?
    //key=AIzaSyAzzimVmu9nBvifyIdwaY-el371vrczEhk
    //&channelId=UCuAXFkgsw1L7xaCfnd5JJOw
    //&part=snippet
    //&order=date
    //&maxResults=50
    //&type=video

    private void doApiCall() {

        Call<VideoModel> videoModelCall = SingltonRetrofitObject.getmInstance().getAPI().getVideosDetails(
                getString(R.string.youtubeAPIKey),
                "UCuAXFkgsw1L7xaCfnd5JJOw",
                "snippet",
                "date",
                "50",
                "video"
        );

        Call<ChannelVideoModel> videoModelCall2 = SingltonRetrofitObject.getmInstance().getAPI().getChannelDetails(
                "snippet,statistics",
                "UCuAXFkgsw1L7xaCfnd5JJOw",
                getString(R.string.youtubeAPIKey)
        );

        videoModelCall.enqueue(new Callback<VideoModel>() {
            @Override
            public void onResponse(Call<VideoModel> call, Response<VideoModel> response) {

                setRecyclerView(response.body().getItems());
            }

            @Override
            public void onFailure(Call<VideoModel> call, Throwable t) {

            }
        });

        videoModelCall2.enqueue(new Callback<ChannelVideoModel>() {
            @Override
            public void onResponse(Call<ChannelVideoModel> call, Response<ChannelVideoModel> response) {

                Ittems[] ittems = response.body().getIttems();
                String title = ittems[0].getSnippet().getTitle();
                String description = ittems[0].getSnippet().getDescription();
                String subscribersCount = ittems[0].getStatistics().getSubscriberCount();
                channelName.setText(title);
                channelDesc.setText(description);
                subscribers.setText("subscribers: " + subscribersCount);


                //Update Firebase
                writeToFireBase(title, description, subscribersCount);
            }

            @Override
            public void onFailure(Call<ChannelVideoModel> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }

    private void setRecyclerView(Items[] items) {

        MyAdapter myAdapter = new MyAdapter(this, items);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    private void writeToFireBase(String cTitle, String cDescription, String cSubscribers) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("astley");
        myRef.setValue("Channel");
        myRef.child("Channel").child("Title").setValue(cTitle);
        myRef.child("Channel").child("Description").setValue(cDescription);
        myRef.child("Channel").child("Subscribers").setValue(cSubscribers);
    }

}