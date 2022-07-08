package com.example.practiceloginfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_GOOGLE_SIGN_IN = 123;
    private final String TAG = this.getClass().getName();
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnSignIn;
    private TextView txtSwitchToSignUp;
    private TextView txtSwitchToResetPassword;
    private FirebaseAuth mAuth;

    // Google
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton btnGoogleLogin;

    // Facebook
    private LoginButton btnFacebookLogin;
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        configureGoogleSignIn();
        configureFacebookSignIn();
        checkUserLoginAndHandle();
        addControls();
        addEvents();
    }

    private void addControls() {
        edtEmail = findViewById(R.id.edtEmailSignIn);
        edtPassword = findViewById(R.id.edtPasswordSignIn);
        btnSignIn = findViewById(R.id.btnSignIn);
        txtSwitchToSignUp = findViewById(R.id.txtSwitchToSignUp);
        txtSwitchToResetPassword = findViewById(R.id.txtSwitchToResetPassword);
        btnGoogleLogin = findViewById(R.id.btnGoogleLogin);
    }

    private void addEvents() {
        btnSignIn.setOnClickListener(view -> handleLogin());

        txtSwitchToSignUp.setOnClickListener(view -> switchToRegister());

        txtSwitchToResetPassword.setOnClickListener(view -> switchToResetPassword());

        btnGoogleLogin.setOnClickListener(view -> loginGoogle());

        btnFacebookLogin.setOnClickListener(view -> loginFacebook());
    }


    private void switchToHomeScreen() {
        Intent intentSignUp = new Intent(LoginActivity.this, HomeScreenActivity.class);
        startActivity(intentSignUp);
    }

    private void switchToRegister() {
        Intent intentSignUp = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intentSignUp);
    }

    private void switchToResetPassword() {
        Intent intentResetPassword = new Intent(LoginActivity.this, ResetPasswordActivity.class);
        startActivity(intentResetPassword);
    }

    private void handleLogin() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        if (Helper.isEmailValid(email) && Helper.isPasswordValid(password)) {
            login(email, password);
        } else {
            Toast.makeText(this, "Email or Password is invalid!", Toast.LENGTH_SHORT).show();
        }
    }

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.e(TAG, "Login : " + Objects.requireNonNull(task.getException()));
                    }
                    checkUserLoginAndHandle();
                });
    }

    // Handle Google Login
    // ...

    private void configureGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id))
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void loginGoogle() {
        Intent loginGoogleIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(loginGoogleIntent, RC_GOOGLE_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // For Facebook
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleSignInResult(task);
        }
    }

    private void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                checkUserLoginAndHandle();
                            } else {
                                Log.e(TAG, "handleGoogleSignInResult -> signInWithCredential: " + Objects.requireNonNull(task.getException()).toString());
                            }
                        }
                    });
        } catch (ApiException e) {
            Log.e(TAG, "handleGoogleSignInResult: " + e.toString());
        }
    }

    private boolean isUserLogin() {
        return mAuth.getCurrentUser() != null;
    }

    private boolean isGoogleLogin() {
        // Check for existing Google Sign In account
        // If the user is already signed in the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        return account != null;
    }

    private void checkUserLoginAndHandle() {
        if (isGoogleLogin() || isUserLogin() || isFacebookLogin()) {
            switchToHomeScreen();
        } else {
            Toast.makeText(this, "Login failed! Email or password is incorrect", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle Facebook Login
    // ...

    private void configureFacebookSignIn() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication());

        mCallbackManager = CallbackManager.Factory.create();
        btnFacebookLogin = findViewById(R.id.btnFacebookLogin);
        btnFacebookLogin.setReadPermissions("email", "public_profile", "user_friends");
    }

    private void loginFacebook() {
        btnFacebookLogin.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(@NonNull FacebookException e) {
                Log.e(TAG, "loginFacebook: " + e);
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential fbCredential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(fbCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            checkUserLoginAndHandle();
                        } else {
                            Log.e(TAG, "handleFacebookAccessToken: " + Objects.requireNonNull(task.getException()).toString());
                        }
                    }
                });
    }

    private boolean isFacebookLogin() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null && !accessToken.isExpired();
    }
}