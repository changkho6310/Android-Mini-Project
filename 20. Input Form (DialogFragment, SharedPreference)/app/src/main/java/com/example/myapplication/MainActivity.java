package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.datalocal.DataLocalManager;

public class MainActivity extends AppCompatActivity implements InputFragment.OnButtonClicked {
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if it's the first time to install the app
        if (DataLocalManager.isFirstInstalled()) {
            Toast.makeText(this, "First installed app", Toast.LENGTH_SHORT).show();
            DataLocalManager.setFirstInstalled(false);
        }

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