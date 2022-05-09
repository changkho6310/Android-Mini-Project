package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.Random;


public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    Button btnStart, btnStop;
    int totalIncreasingUnits = 1;
    boolean isCountdownTimerRunning = false;
    int secondaryProgress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewMapping();
        CountDownTimer countDownTimer = new CountDownTimer(10000, 50) {
            @Override
            public void onTick(long l) {
                int oldProgress = progressBar.getProgress();
                Random random = new Random();
                if (oldProgress >= progressBar.getMax()) {
                    progressBar.setProgress(0);
                    secondaryProgress = random.nextInt(10) * totalIncreasingUnits;
                    progressBar.setSecondaryProgress(secondaryProgress);
                } else {
                    progressBar.setProgress(oldProgress + totalIncreasingUnits);
                    if(secondaryProgress < progressBar.getMax()) {
                        secondaryProgress += random.nextInt(10) * totalIncreasingUnits;
                        progressBar.setSecondaryProgress(secondaryProgress);
                    }
                }
            }

            @Override
            public void onFinish() {
                this.start();
            }
        };
        btnStart.setOnClickListener(view -> {
            if (!isCountdownTimerRunning) {
                countDownTimer.start();
                isCountdownTimerRunning = true;
            }
        });
        btnStop.setOnClickListener(view -> {
            isCountdownTimerRunning = false;
            countDownTimer.cancel();
        });
    }

    private void viewMapping() {
        progressBar = findViewById(R.id.my_progress_horizontal);
        btnStart = findViewById(R.id.btn_start);
        btnStop = findViewById(R.id.btn_stop);
    }
}