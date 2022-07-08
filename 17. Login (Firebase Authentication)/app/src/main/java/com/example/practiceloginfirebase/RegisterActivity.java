package com.example.practiceloginfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    public final String TAG = this.getClass().getName().toString();
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnSignUp;
    private TextView txtSwitchToSignIn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_register);
        addControls();
        addEvents();
    }

    private void addControls() {
        edtEmail = findViewById(R.id.edtEmailSignUp);
        edtPassword = findViewById(R.id.edtPasswordSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtSwitchToSignIn = findViewById(R.id.txtSwitchToSignIn);
    }

    private void addEvents() {
        txtSwitchToSignIn.setOnClickListener(view -> {
            switchToLogin();
        });
        btnSignUp.setOnClickListener(view -> {
            HandleSignUp();
        });
    }

    private void switchToLogin() {
        Intent intentLogin = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intentLogin);
    }

    private void HandleSignUp() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        if (Helper.isEmailValid(email) && Helper.isPasswordValid(password)) {
            signUp(email, password);
        } else {
            Toast.makeText(this, "Email or Password is not valid...", Toast.LENGTH_SHORT).show();
        }
    }

    private void signUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            switchToLogin();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Sign Up failed...\nPls check Loge", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Fail to createUserWithEmailAndPassword: " + Objects.requireNonNull(task.getException()).toString());
                        }
                    }
                });
    }


}
