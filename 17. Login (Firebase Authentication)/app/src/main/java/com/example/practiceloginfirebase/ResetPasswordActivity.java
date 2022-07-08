package com.example.practiceloginfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ResetPasswordActivity extends AppCompatActivity {
    public final String TAG = this.getClass().getName().toString();
    private EditText edtEmail;
    private TextView txtShowMessage;
    private TextView txtShowMessageRequestSent;
    private LinearLayout llShowDirectMessage;
    private Button btnSendEmailRequest;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        mAuth = FirebaseAuth.getInstance();
        addControls();
        addEvents();
    }

    private void addControls() {
        edtEmail = findViewById(R.id.edtEmailResetPassword);
        btnSendEmailRequest = findViewById(R.id.btnConfirmResetPassword);
        txtShowMessage = findViewById(R.id.txtShowMessageResetPassword);
        txtShowMessageRequestSent = findViewById(R.id.txtShowMessageRequestSent);
        llShowDirectMessage = findViewById(R.id.llShowDirectMessage);
    }

    private void addEvents() {
        btnSendEmailRequest.setOnClickListener(view -> {
            String email = edtEmail.getText().toString().trim();
            if (Helper.isEmailValid(email)) {
                sendRequestResetPassword(email);
                showRequestSentMessage();
                redirectToLoginScreen();
            } else {
                txtShowMessage.setVisibility(View.VISIBLE);
            }
        });

        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                txtShowMessage.setVisibility(View.GONE);
            }
        });
    }

    private void showRequestSentMessage() {
        txtShowMessageRequestSent.setVisibility(View.VISIBLE);
        llShowDirectMessage.setVisibility(View.VISIBLE);
    }

    private void redirectToLoginScreen() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switchToLoginScreen();
            }
        }, 3000);
    }

    private void switchToLoginScreen() {
        Intent intentLogin = new Intent(ResetPasswordActivity.this, LoginActivity.class);
        startActivity(intentLogin);
    }

    private void sendRequestResetPassword(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ResetPasswordActivity.this, "An reset password email has been sent!", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendRequestResetPassword: " + Objects.requireNonNull(task.getException()).toString());
                        }
                    }
                });
    }
}
