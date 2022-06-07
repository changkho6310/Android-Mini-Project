package com.example.myapplication.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.Account;
import com.example.myapplication.Presenter.LoginContract;
import com.example.myapplication.Presenter.LoginPresenter;
import com.example.myapplication.R;

public class LoginView extends AppCompatActivity implements LoginContract.View, View.OnClickListener {
    private EditText mTextEmail;
    private EditText mTextPassword;
    private Button mButtonSignIn;
    private TextView mTextLoginResult;
    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);

        initView();
        registerListener();
        initPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.detachView();
    }

    private void initPresenter() {
        mLoginPresenter = new LoginPresenter();
        mLoginPresenter.attachView(this);
    }

    private void registerListener() {
        mButtonSignIn.setOnClickListener(this);
    }

    private void initView() {
        mTextEmail = findViewById(R.id.text_email);
        mTextPassword = findViewById(R.id.text_password);
        mButtonSignIn = findViewById(R.id.button_sign_in);
        mTextLoginResult = findViewById(R.id.text_login_result);
    }

    @Override
    public void loginSuccess() {
        if (mTextLoginResult != null) {
            mTextLoginResult.setText("Login Successfully!");
            mTextLoginResult.setTextColor(getResources().getColor(R.color.teal_700));
            mTextLoginResult.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void loginFailure(String error) {
        if (mTextLoginResult != null) {
            mTextLoginResult.setText(error);
            mTextLoginResult.setTextColor(getResources().getColor(com.google.android.material.R.color.design_default_color_error));
            mTextLoginResult.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_sign_in: {
                clickLogin();
                break;
            }
        }
    }

    private void clickLogin() {
        String email = mTextEmail.getText().toString();
        String password = mTextPassword.getText().toString();
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username or Password is Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        mLoginPresenter.handleLogin(new Account(email, password));
    }
}