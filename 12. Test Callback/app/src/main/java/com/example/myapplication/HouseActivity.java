package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.os.Bundle;

public class HouseActivity extends AppCompatActivity {
    private HusbandFragment mHusbandFragment;
    private WifeFragment mWifeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);
        initFragment();
    }

    private void initFragment() {
        mHusbandFragment = new HusbandFragment();
        mWifeFragment = new WifeFragment();
        mHusbandFragment.setWifeCallback(mWifeFragment);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.container_fragment_husband, mHusbandFragment)
                .add(R.id.container_fragment_wife, mWifeFragment)
                .commit();
    }
}