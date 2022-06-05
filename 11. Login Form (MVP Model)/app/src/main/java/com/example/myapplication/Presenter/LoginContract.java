package com.example.myapplication.Presenter;

import com.example.myapplication.Model.Account;

public interface LoginContract {
    interface View{
        void loginSuccess();
        void loginFailure(String error);
    }
    interface Presenter{
        void handleLogin(Account account);
    }
}
