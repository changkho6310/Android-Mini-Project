package com.example.myapplication.model;

import android.text.TextUtils;
import android.util.Patterns;

public class Account {
    String email;
    String password;

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean isEmailValid() {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean isPasswordValid() {
        return !TextUtils.isEmpty(password) && password.length() >= 6;
    }

    public boolean loginSuccessfully() {
        return isEmailValid()
                && isPasswordValid()
                && email.equals("admin@gmail.com")
                && password.equals("123123");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
