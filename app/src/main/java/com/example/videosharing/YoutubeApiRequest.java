package com.example.videosharing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.videosharing.API.SingltonRetrofitObject;
import com.example.videosharing.channelModel.ChannelVideoModel;
import com.example.videosharing.model.Items;
import com.example.videosharing.channelModel.Ittems;
import com.example.videosharing.model.VideoModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YoutubeApiRequest extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;


    TextView channelName, channelDesc, subscribers;

    private DatabaseReference mDatabase;
    DatabaseReference myRef;
    FirebaseDatabase database;
    GoogleSignInAccount account;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_api_request);

        // instantiate the recycler view elements
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        // instantiate the channel detail views
        channelName = findViewById(R.id.channel_name);
        channelDesc = findViewById(R.id.channel_desc);
        subscribers = findViewById(R.id.subscribers);

        // instantiate the back button
        back = findViewById(R.id.backChannelBtn);

        // set the layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();

        // instantiate the astley firebase database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("astley");


        // Reads database changes of the channel's title in the astley firebase database and updates
        // the GUI accordingly
        ValueEventListener titlePostListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Update the UI
                channelName.setText(String.valueOf(dataSnapshot.getValue()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Read failed
                Log.w("firebase", "title firebase read failed", databaseError.toException());
            }
        };
        myRef.child("Channel").child("Title").addValueEventListener(titlePostListener);

        // Reads database changes of the channel's description in the astley firebase database and
        // updates the GUI accordingly
        ValueEventListener descriptionPostListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Update the UI
                channelDesc.setText(String.valueOf(dataSnapshot.getValue()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Read failed
                Log.w("firebase", "description firebase read failed", databaseError.toException());
            }
        };
        myRef.child("Channel").child("Description").addValueEventListener(descriptionPostListener);

        // Reads database changes of the channel's subscribers in the astley firebase database and
        // updates the GUI accordingly
        ValueEventListener subscribersPostListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Update the UI
                subscribers.setText(String.valueOf(dataSnapshot.getValue()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Read failed
                Log.w("firebase", "subscribers firebase read failed", databaseError.toException());
            }
        };
        myRef.child("Channel").child("Subscribers").addValueEventListener(subscribersPostListener);

        // Takes the user back to the main menu
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main);
            }
        });

        // Check if the user is already signed in
        account = GoogleSignIn.getLastSignedInAccount(this);

        // If the user is not signed in, take them back to the login page
        if (account == null) {
            Intent login = new Intent(getApplicationContext(), Login.class);
            startActivity(login);
        }

        // get the API data
        doApiCall();
    }

    // Gets the API data
    private void doApiCall() {

        // Calls for channel video list information
        // According to network inspector, this took 621ms to complete, I could further optimise this
        // by limiting the maxResult returned
        Call<VideoModel> videoModelCall = SingltonRetrofitObject.getmInstance().getAPI().getVideosDetails(
                getString(R.string.youtubeAPIKey),
                "UCuAXFkgsw1L7xaCfnd5JJOw",
                "snippet",
                "date",
                "50",
                "video"
        );

        // Calls for channel detail information
        // According to network inspector, this took 567ms to complete. I could further optimize this
        // by caching results and monitoring the performance of these two http requests
        Call<ChannelVideoModel> videoModelCall2 = SingltonRetrofitObject.getmInstance().getAPI().getChannelDetails(
                "snippet,statistics",
                "UCuAXFkgsw1L7xaCfnd5JJOw",
                getString(R.string.youtubeAPIKey)
        );

        // Receives the channel video list query
        videoModelCall.enqueue(new Callback<VideoModel>() {
            @Override
            public void onResponse(Call<VideoModel> call, Response<VideoModel> response) {
                // Update the recycler view with the list of channel videos
                setRecyclerView(response.body().getItems());
            }

            @Override
            public void onFailure(Call<VideoModel> call, Throwable t) {
                // Query failed, print error
                System.out.println(t.toString());
            }
        });

        // Receives the channel details query
        videoModelCall2.enqueue(new Callback<ChannelVideoModel>() {
            @Override
            public void onResponse(Call<ChannelVideoModel> call, Response<ChannelVideoModel> response) {
                // Log response
                System.out.println(response.toString());

                // Get channel details
                Ittems[] ittems = response.body().getIttems();
                String title = ittems[0].getSnippet().getTitle();
                String description = ittems[0].getSnippet().getDescription();
                String subscribersCount = ittems[0].getStatistics().getSubscriberCount();

                // Set the GUI with the channel details
                channelName.setText(title);
                channelDesc.setText(description);
                subscribers.setText("subscribers: " + subscribersCount);

                //Update the astley firebase database
                writeToFireBase(title, description, subscribersCount);
            }

            @Override
            public void onFailure(Call<ChannelVideoModel> call, Throwable t) {
                // Query failed, print error
                System.out.println(t.toString());
            }
        });
    }

    // Sets the list of channel videos recycler view
    private void setRecyclerView(Items[] items) {

        // Set the adapter
        MyAdapter myAdapter = new MyAdapter(this, items);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    // Writes channel details to the astley firebase database
    private void writeToFireBase(String cTitle, String cDescription, String cSubscribers) {

        // instantiate the firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("astley");

        // write the values to the database
        myRef.setValue("Channel");
        myRef.child("Channel").child("Title").setValue(cTitle);
        myRef.child("Channel").child("Description").setValue(cDescription);
        myRef.child("Channel").child("Subscribers").setValue(cSubscribers);
    }

}