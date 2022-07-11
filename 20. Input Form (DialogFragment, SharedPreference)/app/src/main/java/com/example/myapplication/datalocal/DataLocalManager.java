package com.example.myapplication.datalocal;

import android.content.Context;

import java.util.Set;

public class DataLocalManager {
    private static final String PREF_FIRST_INSTALL = "PREF_FIRST_INSTALL";
    private static final String PREF_USER_LIST = "USER_LIST";
    private static final String PREF_NAME = "PREF_NAME";
    private static DataLocalManager mInstance;
    private MySharedPreference mMySharedPreference;

    public static void init(Context context) {
        mInstance = new DataLocalManager();
        mInstance.mMySharedPreference = new MySharedPreference(context);
    }

    public static DataLocalManager getInstance() {
        if (mInstance == null) {
            mInstance = new DataLocalManager();
        }
        return mInstance;
    }

    public static void setFirstInstalled(boolean isFirst) {
        DataLocalManager.getInstance().mMySharedPreference
                .putBooleanValue(PREF_FIRST_INSTALL, isFirst);
    }

    public static boolean isFirstInstalled() {
        return DataLocalManager.getInstance().mMySharedPreference.getBooleanValue(PREF_FIRST_INSTALL);
    }

    public static void setPrefUserList(Set<String> prefUserList) {
        DataLocalManager.getInstance().mMySharedPreference
                .putStringSet(PREF_USER_LIST, prefUserList);
    }

    public static Set<String> getUserSet() {
        return DataLocalManager.getInstance().mMySharedPreference
                .getStringSet(PREF_USER_LIST);
    }

    public static void setName(String name) {
        DataLocalManager.getInstance().mMySharedPreference
                .putString(PREF_NAME, name);
    }

    public static String getName() {
        return DataLocalManager.getInstance().mMySharedPreference
                .getString(PREF_NAME);
    }


}
