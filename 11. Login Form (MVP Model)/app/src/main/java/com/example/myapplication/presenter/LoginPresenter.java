package com.example.myapplication.presenter;

import com.example.myapplication.model.Account;

public class LoginPresenter implements LoginContact.Presenter {
    private LoginContact.View mLoginView;

    public LoginPresenter() {
    }

    @Override
    public void login(Account account) {
        if (!account.isEmailValid()) {
            mLoginView.showInvalidEmailResult();
        } else if (!account.isPasswordValid()) {
            mLoginView.showInvalidPasswordResult();
        } else if (account.loginSuccessfully()) {
            mLoginView.showLoginSuccessResult();
        } else {
            mLoginView.showLoginFailedResult();
        }
    }

    @Override
    public void attachView(LoginContact.View view) {
        mLoginView = view;
    }

    @Override
    public void detachView() {
        mLoginView = null;
    }
}
