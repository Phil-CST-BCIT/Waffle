package com.bcit.comp3717.waffle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class SuccessCustomerLoggedIn extends AppCompatActivity {

    ImageView imageView;
    TextView name, phone, id;
    // TextView email;
    Button signOut;

    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_customer_logged_in);

        // [START config_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // [END config_signin]

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // imageView = findViewById(R.id.imageView);
        name = findViewById(R.id.textName);
        // email = findViewById(R.id.textEmail);
         id = findViewById(R.id.textId);
       // phone = findViewById(R.id.textPhone);
        signOut = findViewById(R.id.signOutbtn);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // ...
                    case R.id.signOutbtn:
                        signOut();
                        break;
                    // ...
                }
            }
        });

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            // String personGivenName = acct.getGivenName();
            // String personFamilyName = acct.getFamilyName();
            // String personEmail = acct.getEmail();
            String personId = acct.getId();
            // Uri personPhoto = acct.getPhotoUrl();

            name.setText(personName);
            id.setText(personId);
        }
    }

    public void signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // Add a pop-up shows successfully log out
                        Toast.makeText(SuccessCustomerLoggedIn.this, "Signed out successfully", Toast.LENGTH_LONG).show();
                        finish();

                        // After signing out, go back to Sign In page
                        // Intent i = new Intent(SuccessCustomerLoggedIn.this, MainActivity.class);
                        // startActivity(i);
                    }
                });
        // [END auth_fui_signout]
    }
}