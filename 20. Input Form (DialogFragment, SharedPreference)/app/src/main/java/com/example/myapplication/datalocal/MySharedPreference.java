package com.example.myapplication.datalocal;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class MySharedPreference {
    public static final String MY_SHARED_PREFERENCE = "MY_SHARED_PREFERENCE";
    private Context mContext;

    public MySharedPreference(Context context) {
        this.mContext = context;
    }

    public void putBooleanValue(String key, boolean value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBooleanValue(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCE,
                Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, true);
    }

    public void putStringSet(String key, Set<String> stringSet) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(key, stringSet);
        editor.apply();
    }

    public Set<String> getStringSet(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCE,
                Context.MODE_PRIVATE);
        return sharedPreferences.getStringSet(key, null);
    }

    public void putString(String key, String value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCE,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }


}
