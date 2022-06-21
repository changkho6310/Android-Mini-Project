package com.example.myapplication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Account;
import com.example.myapplication.presenter.LoginContact;
import com.example.myapplication.presenter.LoginPresenter;

public class LoginView extends AppCompatActivity implements LoginContact.View, View.OnClickListener {
    private TextView txtResult;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;
    private LoginContact.Presenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnLogin.setOnClickListener(this);
    }

    private void addControls() {
        mLoginPresenter = new LoginPresenter();
        mLoginPresenter.attachView(this);

        txtResult = findViewById(R.id.txtResult);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void showResult() {
        txtResult.setVisibility(View.VISIBLE);
    }

    private void setResultErrorColor() {
        txtResult.setTextColor(getResources().getColor(R.color.red));
    }

    @Override
    public void showLoginSuccessResult() {
        showResult();
        txtResult.setTextColor(getResources().getColor(R.color.blue));
        txtResult.setText(getResources().getString(R.string.login_success_result));
    }

    @Override
    public void showInvalidEmailResult() {
        showResult();
        setResultErrorColor();
        txtResult.setText(getResources().getString(R.string.invalid_email_result));
    }

    @Override
    public void showInvalidPasswordResult() {
        showResult();
        setResultErrorColor();
        txtResult.setText(getResources().getString(R.string.invalid_password_result));
    }


    @Override
    public void showLoginFailedResult() {
        showResult();
        setResultErrorColor();
        txtResult.setText(getResources().getString(R.string.login_failed_result));
    }

    private void clickLogin() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        Account account = new Account(email, password);
        mLoginPresenter.login(account);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLogin) {
            clickLogin();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.detachView();
    }
}