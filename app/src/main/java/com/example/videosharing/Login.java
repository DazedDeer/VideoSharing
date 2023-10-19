package com.example.videosharing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class Login extends AppCompatActivity {

    GoogleSignInAccount account;
    GoogleSignInClient mGoogleSignInClient;

    // Sign in success code
    int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Check for a previous sign in, if the user is already signed in, the GoogleSignInAccount
        // will not be null
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        // If the user is already signed in, take them to the main menu
        if(account != null) {
            goToMain();
        }

        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.signIn);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        // Set the sign in button's on click listener
        signInButton.setOnClickListener(this::onClick);
    }

    // Method to take the user back to the main menu
    private void goToMain() {
        Intent main = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(main);
    }


    // Launches the signIn() method when the sign in button is clicked
    public void onClick(View v) {
        if (v.getId() == R.id.signIn) {
                signIn();
        }
    }

    // Signs the user in
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // Prepare sign in
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the GoogleSignInClient Intent
        if (requestCode == RC_SIGN_IN) {
            // Make a googleSignIn task
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    // Handle the signIn request
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            // Try to create an account
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, bring the user to the main menu
            goToMain();

        } catch (ApiException e) {
            // Sign in failed
            Log.w("Warning", "signInResult:failed code=" + e.getStatusCode());
        }
    }
}