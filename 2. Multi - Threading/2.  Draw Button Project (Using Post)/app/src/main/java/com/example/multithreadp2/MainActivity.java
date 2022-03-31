package com.example.multithreadp2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText edtNumberOfButtons;
    private Button btnDraw;
    private TextView tvPercent;
    private LinearLayout llButtonsContainer;
    private LinearLayout.LayoutParams layoutParams;
    private int totalButtons = 0;
    int percent = 0;
    int value = 0;

    Handler handler = new Handler();

    // Can update UI when backgroundThread call handler.post(foregroundThread)
    Runnable foregroundThread = new Runnable() {
        @Override
        public void run() {
            tvPercent.setText(percent + "%");
            if (percent == 100) {
                Toast.makeText(MainActivity.this, "backgroundThread finished", Toast.LENGTH_SHORT).show();
            } else {
                Button newButton = new Button(MainActivity.this);
                newButton.setText("Button " + value);
                newButton.setLayoutParams(layoutParams);
                llButtonsContainer.addView(newButton);
                newButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        newButton.setTextColor(Color.RED);
                    }
                });
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDrawButtons();
            }
        });
    }

    private void handleDrawButtons() {
        llButtonsContainer.removeAllViews();
        String tmpNumberOfButtons = edtNumberOfButtons.getText().toString();
        if (tmpNumberOfButtons.equals("")) {
            Toast.makeText(this, "Pls input number of buttons to draw", Toast.LENGTH_SHORT).show();
            return;
        }
        totalButtons = Integer.parseInt(tmpNumberOfButtons);
        if (totalButtons == 0) {
            return;
        }
        // Thread cannot update UI
        // Call handler.post(foregroundThread) to update UI
        Thread backgroundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < totalButtons; i++) {
                    percent = i * 100 / totalButtons;
                    value = i;
                    handler.post(foregroundThread);
                    SystemClock.sleep(200);
                }
                percent = 100;
                handler.post(foregroundThread);
            }
        });
        backgroundThread.start();
    }

    private void addControls() {
        edtNumberOfButtons = findViewById(R.id.edt_number_of_buttons);
        btnDraw = findViewById(R.id.btn_draw);
        tvPercent = findViewById(R.id.tv_percent);
        llButtonsContainer = findViewById(R.id.ll_btn_container);
        layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
    }
}