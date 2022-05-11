package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_demo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String strTemp;
        switch (item.getItemId()) {
            case R.id.menu_search: {
                Toast.makeText(this, getResources().getString(R.string.search), Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.menu_settings: {
                Toast.makeText(this, getResources().getString(R.string.settings), Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.menu_share: {
                Toast.makeText(this, getResources().getString(R.string.share), Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.menu_contact_email: {
                Toast.makeText(this, getResources().getString(R.string.email), Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.menu_contact_phone: {
                Toast.makeText(this, getResources().getString(R.string.phone), Toast.LENGTH_SHORT).show();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}