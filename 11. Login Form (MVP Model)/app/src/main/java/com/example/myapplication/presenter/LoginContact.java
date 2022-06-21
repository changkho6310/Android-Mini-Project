package com.example.myapplication.presenter;

import com.example.myapplication.model.Account;

public interface LoginContact {
    interface Presenter {
        void login(Account account);

        void attachView(View view);

        void detachView();
    }

    interface View {
        void showLoginSuccessResult();

        void showLoginFailedResult();

        void showInvalidEmailResult();

        void showInvalidPasswordResult();
    }
}
