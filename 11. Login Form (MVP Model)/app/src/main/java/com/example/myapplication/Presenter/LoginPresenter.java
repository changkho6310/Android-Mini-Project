package com.example.myapplication.Presenter;

import android.view.View;

import com.example.myapplication.Model.Account;

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View mView;

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

    @Override
    public void attachView(LoginContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
