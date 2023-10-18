package com.example.videosharing;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
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
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.videosharing.API.SingltonRetrofitObject;
import com.example.videosharing.model.ChannelInfo;
import com.example.videosharing.model.Items;
import com.example.videosharing.model.VideoModel;
import com.example.videosharing.viewModel.YoutubeApiViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.firebase.FirebaseApp;

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
    RecyclerView recyclerView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_api_request);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
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

        videoModelCall.enqueue(new Callback<VideoModel>() {
            @Override
            public void onResponse(Call<VideoModel> call, Response<VideoModel> response) {

                setRecyclerView(response.body().getItems());
            }

            @Override
            public void onFailure(Call<VideoModel> call, Throwable t) {

            }
        });
    }

    private void setRecyclerView(Items[] items) {

        MyAdapter myAdapter = new MyAdapter(this, items);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
}