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

public class YoutubePlayer extends AppCompatActivity {
    SignInCredential credential;
    YouTubePlayerView youTubePlayerView;
    EditText videoUrlEt;
    String videoId;

    final String youTubeUrlRegex = "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/";
    final String[] videoIdRegex = { "\\?vi?=([^&]*)","watch\\?.*v=([^&]*)", "(?:embed|vi?)/([^/?]*)", "^([A-Za-z0-9\\-]*)"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);


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
            Picasso.with(this).load(account.getPhotoUrl().toString()).into(avatarView);

            // initialising the GUI widgets for Video Player and user input
            youTubePlayerView = findViewById(R.id.youtube_player_view);
            youTubePlayerView.setEnableAutomaticInitialization(false);
            videoUrlEt = findViewById(R.id.ytVideoUrlEt);
            Button playBtn = findViewById(R.id.ytPlayVideoBtn);
            // setup the click event for the button
            playBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // calling method to play the video
                    playVideoButtonClick();
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
                    videoId = "dQw4w9WgXcQ";
                    youTubePlayer.loadVideo(videoId, 0);
                }
            });
        }

        public String extractVideoIdFromUrl(String url) {
            String youTubeLinkWithoutProtocolAndDomain = youTubeLinkWithoutProtocolAndDomain(url);
            // extract the VideoID and return it
            for(String regex : videoIdRegex) {
                Pattern compiledPattern = Pattern.compile(regex);
                Matcher matcher = compiledPattern.matcher(youTubeLinkWithoutProtocolAndDomain);
                if(matcher.find()) {
                    return matcher.group(1);
                }
            }
            return null;
        }

        private String youTubeLinkWithoutProtocolAndDomain(String url) {
            // matches the domain and protocol part like first part of the following urls and delete
            // them for url input: https://www.youtube.com/watch?v=KAbJnGLDxnE
            Pattern compiledPattern = Pattern.compile(youTubeUrlRegex);
            Matcher matcher = compiledPattern.matcher(url);
            if (matcher.find()) {
                return url.replace(matcher.group(), "");
            }
            return url;
        }
        public void playVideoButtonClick() {
            // check if the user has entered a video url
            String urlStr = videoUrlEt.getText().toString();
            // if they haven't and its empty, load the default video
            if(urlStr.isEmpty()) {
                videoId = "dQw4w9WgXcQ";
            } else {
                // otherwise get the videoID from the URL entered and store it in videoID
                videoId = extractVideoIdFromUrl(urlStr);
            }
            // if a valid URL is provided play the video
            if(videoId != null) {
                youTubePlayerView.getYouTubePlayerWhenReady(this::playVideo);
            } else {
                // otherwise, let the user know the URL is not correct
                Toast.makeText(this, "Enter a valid YouTube video URL to play a video", Toast.LENGTH_LONG).show();
            }
        }
        // Method to play the video in the Video Player
    private  void playVideo(YouTubePlayer player) {
        player.loadVideo(videoId, 0);
    }
}