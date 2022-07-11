package com.example.myapplication;

import android.app.Application;

import com.example.myapplication.datalocal.DataLocalManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManager.init(getApplicationContext());
    }
}
