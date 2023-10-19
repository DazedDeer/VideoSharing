package com.example.videosharing;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextView welcome;
    Button watch_btn, channel_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate Views
        watch_btn = findViewById(R.id.watch_video_btn);
        channel_btn = findViewById(R.id.list_video_btn);
        welcome = findViewById(R.id.welcome);



        // Check if the user has already signed in
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        FirebaseApp.initializeApp(this);


        if(account == null) {
            // Send the user to login if they are not signed in
            Intent login = new Intent(getApplicationContext(), Login.class);
            startActivity(login);
        } else {
            // The user is signed in, display their details
            String userName = account.getDisplayName();
            welcome.setText("Welcome to Video Sharing (Rick Astley Edition), " + userName);
            String name = account.getDisplayName();
            Uri avatar = account.getPhotoUrl();
        }

        // Takes the user to the video player
        watch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent player = new Intent(getApplicationContext(), YoutubePlayer.class);
                startActivity(player);
            }
        });

        // Takes the user to the Rick Astley Channel information activity
        channel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent channelList = new Intent(getApplicationContext(), YoutubeApiRequest.class);
                startActivity(channelList);
            }
        });
    }
}