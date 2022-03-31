package com.example.multithreadp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
    private LinearLayout.LayoutParams layoutParams =
            new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
    private int totalButtons = 0;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            tvPercent.setText(String.valueOf(msg.arg1) + "%");
            String value = msg.obj.toString();
            if (value.equals("END")) {
                Toast.makeText(MainActivity.this, "Thread is done!", Toast.LENGTH_SHORT).show();
            } else {
                Button newButton = new Button(MainActivity.this);
                newButton.setText(value);
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

    private void handleDrawButtons() {
        totalButtons = Integer.parseInt(edtNumberOfButtons.getText().toString());
        if (totalButtons != 0) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < totalButtons; i++) {
                        Message message = handler.obtainMessage();
                        int percent = i * 100 / totalButtons;
                        message.arg1 = percent;
                        message.obj = "Button " + (i + 1);
                        handler.sendMessage(message);
                        SystemClock.sleep(500);
                    }
                    // Ra khỏi vòng for => Xong (Nhưng chưa chắc percent = 100% [maybe just 98,99%]
                    // Phải truyền message báo hiệu đã xong (percent = 100%)
                    Message message = handler.obtainMessage();
                    message.arg1 = 100;
                    message.obj = "END";
                    handler.sendMessage(message);
                }
            });
            thread.start();
        }
    }

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

    private void addControls() {
        edtNumberOfButtons = findViewById(R.id.edt_number_of_buttons);
        btnDraw = findViewById(R.id.btn_draw);
        tvPercent = findViewById(R.id.tv_percent);
        llButtonsContainer = findViewById(R.id.ll_btn_container);
    }
}