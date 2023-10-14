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
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class Login extends AppCompatActivity {

    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    private ActivityResultLauncher<IntentSenderRequest> signInRequestLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        oneTapClient = Identity.getSignInClient(this);
        signInRequest = BeginSignInRequest.builder().setGoogleIdTokenRequestOptions(
                        BeginSignInRequest.GoogleIdTokenRequestOptions.builder().setSupported(true)
                                // Your server's client ID, not your Android client ID.
                                .setServerClientId(getString(R.string.web_client_id))
                                // Only show accounts previously used to sign in.
                                .setFilterByAuthorizedAccounts(false)
                                .build()
                )
                .setAutoSelectEnabled(true)
                .build();

        SignInButton signInButton = findViewById(R.id.signIn);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInOnClick(view);
            }
        });

        signInRequestLauncher =
                registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(),
                        new ActivityResultCallback<ActivityResult>() {
                            @Override
                            public void onActivityResult(ActivityResult result) {
                                if(result.getResultCode() == Activity.RESULT_OK){
                                    // sign in successful
                                    try {
                                        SignInCredential credential = oneTapClient
                                                .getSignInCredentialFromIntent(result.getData());
                                        String idToken = credential.getGoogleIdToken();
                                        if (idToken != null) {
                                            // Got an ID token from Google to use for authentication
                                            //Intent intent = new Intent(
                                            //  getApplicationContext(),
                                            //  YouTubePlayerActivtiy.class);
                                            //intent.putExtra("CREDENTIAL", credential);
                                            // startActivity(intent);
                                        }
                                    } catch (ApiException e) {
                                        Log.d("API Exception", "issue with sign in");
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
    }

    public void signInOnClick(View view) {
        oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(this, new OnSuccessListener<BeginSignInResult>() {
                    @Override
                    public void onSuccess(BeginSignInResult beginSignInResult) {
                        IntentSenderRequest intentSenderRequest =
                                new IntentSenderRequest.Builder(beginSignInResult.getPendingIntent().getIntentSender())
                                        .build();
                        signInRequestLauncher.launch(intentSenderRequest);
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("FAILURE", "sign in failed!");
                        e.printStackTrace();
                    }
                });
    }

}