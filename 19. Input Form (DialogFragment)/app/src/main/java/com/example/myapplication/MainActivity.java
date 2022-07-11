package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.PersistableBundle;

public class MainActivity extends AppCompatActivity implements InputFragment.OnButtonClicked {
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();

        if (savedInstanceState == null) {
            // Activity run for the first time
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.fmInput, new InputFragment(), "fmInput")
                    .add(R.id.fmList, new UserListFragment(), "fmList")
                    .setReorderingAllowed(true)
                    .commit();
        } else {
            // Activity restart

            // How to store data in Fragment and
            // restore them when activity restart (Orientation change)?

        }
    }


    @Override
    public void onClick(String name) {
        //  ShowUserListFragment fragment = (ShowUserListFragment) fm.findFragmentByTag("fmList");
        //  or
        UserListFragment fragment = (UserListFragment) fm.findFragmentById(R.id.fmList);
        if (fragment != null) {
            fragment.addUser(name);
        }
    }
}