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

    YouTubePlayerView youTubePlayerView;
    EditText videoUrlEt;
    String videoId;

    final String[] videoIdRegex = { "\\?vi?=([^&]*)","watch\\?.*v=([^&]*)", "(?:embed|vi?)/([^/?]*)", "^([A-Za-z0-9\\-]*)"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);

            // Check if the user is already signed in
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

            // If the user isn't signed in, take them to the login page
            if(account == null) {
                Intent login = new Intent(getApplicationContext(), Login.class);
                startActivity(login);
            }

            // Instantiate the user views
            TextView nameTv = findViewById(R.id.userNameTV);
            ImageView avatarView = findViewById(R.id.avatarImage);

            // Fill the user views with the user's details
            nameTv.setText(account.getDisplayName());
            Picasso.get().load(account.getPhotoUrl()).into(avatarView);

            // Instantiate the player GUI
            youTubePlayerView = findViewById(R.id.youtube_player_view);
            youTubePlayerView.setEnableAutomaticInitialization(false);
            videoUrlEt = findViewById(R.id.ytVideoUrlEt);

            // Instantiate the buttons
            Button playBtn = findViewById(R.id.ytPlayVideoBtn);
            Button backBtn = findViewById(R.id.backBtn);

            // Click event that plays the video
            playBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Plays the video
                    playVideoButtonClick();
                }
            });

            // Takes the user back to the main menu
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent back = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(back);
                }
            });

            // Makes the player only play when it is visible to the user
            getLifecycle().addObserver(youTubePlayerView);

            // Instantiate the player with a default video
            youTubePlayerView.initialize(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    super.onReady(youTubePlayer);
                    videoId = "dQw4w9WgXcQ";
                    youTubePlayer.loadVideo(videoId, 0);
                }
            });
        }

        // Extract the video ID
        public String extractVideoIdFromUrl(String url) {
            String youTubeLinkWithoutProtocolAndDomain = youTubeLinkWithoutProtocolAndDomain(url);

            // Extract and return the video ID
            for(String regex : videoIdRegex) {
                Pattern compiledPattern = Pattern.compile(regex);
                Matcher matcher = compiledPattern.matcher(youTubeLinkWithoutProtocolAndDomain);
                if(matcher.find()) {
                    return matcher.group(1);
                }
            }
            return null;
        }

        // Checks if the URL is valid
        private String youTubeLinkWithoutProtocolAndDomain(String url) {

            // URL regex
            String pattern = "^(http(s)?:\\/\\/)?((w){3}.)?youtu(be|.be)?(\\.com)?\\/.+";
            if(!url.isEmpty() && url.matches(pattern)) {
                // valid URL
                return url;
            } else {
                // invalid URL
                Toast.makeText(this, "Invalid URL", Toast.LENGTH_LONG).show();
                return "";
            }
        }

        // Plays the video from the user's URL
        private void playVideoButtonClick() {
            // Get the URL
            String urlStr = videoUrlEt.getText().toString();
            // Check if the URL is empty
            if(urlStr.isEmpty()) {
                // URL is empty, assign a default ID
                videoId = "dQw4w9WgXcQ";
            } else {
                // URL is not empty, extract a video ID
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