package com.example.myapplication.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.example.myapplication.model.Account;
import com.example.myapplication.BR;

public class LoginViewModel extends BaseObservable {
    private String email;
    private String password;
    public ObservableField<String> message = new ObservableField<>();
    public ObservableField<Boolean> isShowMessage = new ObservableField<>(false);
    public ObservableField<Boolean> isLoginSuccess = new ObservableField<>(false);

    public LoginViewModel() {
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    public void onClickLogin() {
        Account account = new Account(getEmail(), getPassword());
        boolean isEmailValid = account.isEmailValid();
        boolean isPasswordValid = account.isPasswordValid();

        isShowMessage.set(true);

        if (!(isEmailValid || isPasswordValid)) {
            message.set("Email and password is not valid...");
        } else if (!isEmailValid) {
            message.set("Email is not valid...");
        } else if (!isPasswordValid) {
            message.set("Password is not valid...");
        } else {
            message.set("Login successfully!");
            isLoginSuccess.set(true);
        }
    }
}
