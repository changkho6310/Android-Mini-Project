package com.example.myapplication.Presenter;

import com.example.myapplication.Model.Account;

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View mView;

    public void setView(LoginContract.View view) {
        mView = view;
    }

    @Override
    public void handleLogin(Account account) {
        if (!account.isValidEmail()) {
            mView.loginFailure("Your email is incorrect!");
        } else if (!account.isValidPassword()) {
            mView.loginFailure("Your password has at least 6 characters!");
        } else {
            mView.loginSuccess();
        }
    }
}
