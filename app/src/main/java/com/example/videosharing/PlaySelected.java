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

// Play the channel's selected video from the list of channel videos
public class PlaySelected extends AppCompatActivity {

    SignInCredential credential;
    YouTubePlayerView youTubePlayerView;
    TextView title;
    String videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_selected);

        // Check if the user is already signed in
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        // If the user is not signed in, bring them to the log in page
        if(account == null) {
            Intent login = new Intent(getApplicationContext(), Login.class);
            startActivity(login);
        }

        // Instantiate the user Views
        TextView nameTv = findViewById(R.id.userNameTV);
        ImageView avatarView = findViewById(R.id.avatarImage);

        // Fill the views with the user's information
        nameTv.setText(account.getDisplayName());
        Picasso.get().load(account.getPhotoUrl()).into(avatarView);
        title = findViewById(R.id.selected_title);

        // initiate the selected video's video player
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        youTubePlayerView.setEnableAutomaticInitialization(false);

        // instantiate the back button
        Button backBtn = findViewById(R.id.backBtn);

        // Get the selected video's details
        Intent intent = getIntent();
        String videoId = intent.getExtras().getString("id");
        String videoTitle = intent.getExtras().getString("title");

        // Set the video's title
        title.setText(videoTitle);

        // takes the user back to the channel information activity
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(getApplicationContext(), YoutubeApiRequest.class);
                startActivity(back);
            }
        });

        // Makes the video play only when the user is looking at the activity
        getLifecycle().addObserver(youTubePlayerView);

        // Instantiate the player with the selected video
        youTubePlayerView.initialize(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                youTubePlayer.loadVideo(videoId, 0);
            }
        });
    }
}