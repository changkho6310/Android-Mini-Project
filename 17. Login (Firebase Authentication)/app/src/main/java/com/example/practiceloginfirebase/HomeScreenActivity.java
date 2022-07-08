package com.example.practiceloginfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

public class HomeScreenActivity extends AppCompatActivity {
    public final String TAG = this.getClass().getName();
    private EditText edtEmail;
    private EditText edtDisplayName;
    private TextView txtShowEmailVerified;
    private TextView txtSendVerification;
    private Button btnUpdate;

    private FirebaseAuth mAuth;
    private FirebaseUser user;


    // Google
    private GoogleSignInClient mGoogleSignInClient;

    // Facebook
    private LoginManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        initial();
        addControls();
        addData();
        addEvents();
    }

    private void initial() {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        // Google
        configureGoogleSignIn();
    }

    private void addControls() {
        edtEmail = findViewById(R.id.edtEmailHomeScreen);
        edtDisplayName = findViewById(R.id.edtNameHomeScreen);
        btnUpdate = findViewById(R.id.btnUpdateHomeScreen);
        txtShowEmailVerified = findViewById(R.id.txtIsEmailVerified);
        txtSendVerification = findViewById(R.id.txtSendVerification);
    }

    private void addEvents() {
        btnUpdate.setOnClickListener(view -> {
            String email = edtEmail.getText().toString().trim();
            String dislayName = edtDisplayName.getText().toString().trim();
            if (Helper.isEmailValid(email) && !dislayName.isEmpty()) {
                updateProfile(email, dislayName);
            }
        });

        txtSendVerification.setOnClickListener(view -> sendVerificationEmail());
    }

    private void addData() {
        if (user != null) {
            edtEmail.setText(user.getEmail());
            edtDisplayName.setText(user.getDisplayName());
            if (user.isEmailVerified()) {
                txtShowEmailVerified.setText(getString(R.string.msg_email_verified));
                txtShowEmailVerified.setTextColor(getColor(R.color.green));
                txtSendVerification.setVisibility(View.GONE);
            }
        }
    }

    private void updateProfile(String email, String displayName) {
        updateDisplayName(displayName);
        updateEmail(email);
    }

    private void updateDisplayName(String displayName) {
        if (user != null) {
            if (!displayName.equals(user.getDisplayName())) {
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(displayName)
                        .build();

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(HomeScreenActivity.this, "DisplayName updated", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e(TAG, "Update profile: " + Objects.requireNonNull(task.getException()));
                            }
                        });
            }
        }
    }

    private void updateEmail(String email) {
        if (!email.equals(user.getEmail())) {
            user.updateEmail(email).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(HomeScreenActivity.this, "Email updated", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "Update Email: " + Objects.requireNonNull(task.getException()));
                }
            });
        }
    }

    private void sendVerificationEmail() {
        user.sendEmailVerification().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(HomeScreenActivity.this, "Email Verification has been sent", Toast.LENGTH_SHORT).show();
            } else {
                Log.e(TAG, "Send Email Verification: " + Objects.requireNonNull(task.getException()));
            }
        });
    }

    private void logOut() {
        // Firebase Logout
        if (mAuth != null) {
            mAuth.signOut();
        }

        // Google Logout
        if (mGoogleSignInClient != null) {
            mGoogleSignInClient.signOut();
        }

        // Facebook Logout
        LoginManager.getInstance().logOut();

        switchToLogin();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_homescreen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_logout:
                logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void switchToLogin() {
        Intent intentLogin = new Intent(HomeScreenActivity.this, LoginActivity.class);
        startActivity(intentLogin);
    }

    // Google
    private void configureGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id))
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
}