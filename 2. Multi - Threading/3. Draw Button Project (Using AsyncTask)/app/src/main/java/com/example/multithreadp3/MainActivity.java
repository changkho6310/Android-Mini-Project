package com.example.multithreadp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
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
        String tmpNumberOfButtons = edtNumberOfButtons.getText().toString();
        if (tmpNumberOfButtons.equals("")) {
            Toast.makeText(this, "Pls input number of buttons to draw", Toast.LENGTH_SHORT).show();
            return;
        }
        int totalButtons = Integer.parseInt(tmpNumberOfButtons);
        if (totalButtons == 0) {
            return;
        }

        HandleButtonTask handleButtonTask = new HandleButtonTask();
        handleButtonTask.execute(totalButtons);
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
    class HandleButtonTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        // Execute when progress is starting
        protected void onPreExecute() {
            super.onPreExecute();
            tvPercent.setText("0%");
            llButtonsContainer.removeAllViews();
        }

        @Override
        // Execute automatically when progress is end (done)
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            tvPercent.setText("100%");
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            int totalButtons = integers[0];
            for (int i = 0; i < totalButtons; i++) {
                int percent = i * 100 / totalButtons;
                publishProgress(percent, i);
                SystemClock.sleep(200);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int percent = values[0];
            int value = values[1];

            tvPercent.setText(String.valueOf(percent));
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
}