package com.example.videosharing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaySelected extends AppCompatActivity {

    SignInCredential credential;
    YouTubePlayerView youTubePlayerView;
    TextView title;
    String videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_selected);
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if(account == null) {
            Intent login = new Intent(getApplicationContext(), Login.class);
            startActivity(login);
        }

        credential = getIntent().getParcelableExtra("CREDENTIAL");

        TextView nameTv = findViewById(R.id.userNameTV);
        ImageView avatarView = findViewById(R.id.avatarImage);
        nameTv.setText(account.getDisplayName());
        Picasso.get().load(account.getPhotoUrl()).into(avatarView);
        title = findViewById(R.id.selected_title);
        //Picasso.with(this).load(account.getPhotoUrl().toString()).into(avatarView);

        // initialising the GUI widgets for Video Player and user input
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        youTubePlayerView.setEnableAutomaticInitialization(false);
        Button backBtn = findViewById(R.id.backBtn);

        Intent intent = getIntent();
        String videoId = intent.getExtras().getString("id");
        String videoTitle = intent.getExtras().getString("title");
        title.setText(videoTitle);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(getApplicationContext(), YoutubeApiRequest.class);
                startActivity(back);
            }
        });

        // YouTubePlayer is a lifecycle aware widget, Add a lifecycle observer so the video only
        // plays when it is visible to the user
        getLifecycle().addObserver(youTubePlayerView);

        // initialising the YouTubePlayerView and load the default video to play
        youTubePlayerView.initialize(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                youTubePlayer.loadVideo(videoId, 0);
            }
        });
    }
    private  void playVideo(YouTubePlayer player) {
        player.loadVideo(videoId, 0);
    }
}