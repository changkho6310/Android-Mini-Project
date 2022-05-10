package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView txtPoint, txtWinner;
    RadioButton radDog, radCat, radTurtle;
    SeekBar seekBarDog, seekBarCat, seekBarTurtle;
    ImageButton imageButtonPlay;
    final int INCREASE_UNITS = 10;
    int point = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mappingViews();
        disableAllSeekBars();

        imageButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRadioButtonChecked()) {
                    disableAllRadioButtons();
                    resetGame();
                    race();
                } else {
                    Toast.makeText(MainActivity.this, R.string.message_bet_animal, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getWinner(int dogProgress, int catProgress, int turtleProgress) {
        if (dogProgress >= catProgress && dogProgress >= turtleProgress) {
            return "Dog";
        } else if (catProgress >= dogProgress && catProgress >= turtleProgress) {
            return "Cat";
        } else {
            return "Turtle";
        }
    }

    private void showWinner(String winner) {
        txtWinner.setText(winner + " won!");
    }

    private void clearWinner() {
        txtWinner.setText("");
    }

    private void handlePointAndShowMessage(String winner) {
        switch (winner) {
            case "Dog": {
                if (radDog.isChecked()) {
                    point += 10;
                    Toast.makeText(this, R.string.message_for_winner, Toast.LENGTH_LONG).show();
                } else {
                    point -= 5;
                    Toast.makeText(this, R.string.message_for_looser, Toast.LENGTH_LONG).show();
                }
                break;
            }
            case "Cat": {
                if (radCat.isChecked()) {
                    point += 10;
                    Toast.makeText(this, R.string.message_for_winner, Toast.LENGTH_LONG).show();
                } else {
                    point -= 5;
                    Toast.makeText(this, R.string.message_for_looser, Toast.LENGTH_LONG).show();
                }
                break;
            }
            case "Turtle": {
                if (radTurtle.isChecked()) {
                    point += 10;
                    Toast.makeText(this, R.string.message_for_winner, Toast.LENGTH_LONG).show();
                } else {
                    point -= 5;
                    Toast.makeText(this, R.string.message_for_looser, Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
        txtPoint.setText(String.valueOf(point));
    }

    private void resetGame() {
        seekBarDog.setProgress(2);
        seekBarCat.setProgress(2);
        seekBarTurtle.setProgress(2);
    }

    private void race() {
        hintButton();
        clearWinner();
        Random random = new Random();
        CountDownTimer countDownTimer = new CountDownTimer(10000, 200) {
            @Override
            public void onTick(long l) {
                int dogProgress = seekBarDog.getProgress();
                int catProgress = seekBarCat.getProgress();
                int turtleProgress = seekBarTurtle.getProgress();
                int maxProgress = seekBarDog.getMax();
                if (dogProgress >= maxProgress || catProgress >= maxProgress || turtleProgress >= maxProgress) {
                    this.cancel();
                    String winner = getWinner(dogProgress, catProgress, turtleProgress);
                    handlePointAndShowMessage(winner);
                    showWinner(winner);
                    enableAllRadioButtons();
                    showButton();
                } else {
                    seekBarDog.setProgress(dogProgress + random.nextInt(INCREASE_UNITS));
                    seekBarCat.setProgress(catProgress + random.nextInt(INCREASE_UNITS));
                    seekBarTurtle.setProgress(turtleProgress + random.nextInt(INCREASE_UNITS));
                }
            }

            @Override
            public void onFinish() {
                this.start();
            }
        };
        countDownTimer.start();
    }

    private void showButton() {
        imageButtonPlay.setVisibility(View.VISIBLE);
    }

    private void hintButton() {
        imageButtonPlay.setVisibility(View.GONE);
    }

    private void mappingViews() {
        txtPoint = findViewById(R.id.tv_initial_point);
        txtWinner = findViewById(R.id.tv_winner);
        imageButtonPlay = findViewById(R.id.imageButtonPlay);
        radDog = findViewById(R.id.rad_dog);
        radCat = findViewById(R.id.rad_cat);
        radTurtle = findViewById(R.id.rad_turtle);
        seekBarDog = findViewById(R.id.seekBar_dog);
        seekBarCat = findViewById(R.id.seekBar_cat);
        seekBarTurtle = findViewById(R.id.seekBar_turtle);
    }

    private void disableAllRadioButtons() {
        radCat.setEnabled(false);
        radDog.setEnabled(false);
        radTurtle.setEnabled(false);
    }

    private void disableAllSeekBars() {
        seekBarDog.setEnabled(false);
        seekBarCat.setEnabled(false);
        seekBarTurtle.setEnabled(false);
    }

    private void enableAllRadioButtons() {
        radCat.setEnabled(true);
        radDog.setEnabled(true);
        radTurtle.setEnabled(true);
    }

    boolean isRadioButtonChecked() {
        return radDog.isChecked() || radCat.isChecked() || radTurtle.isChecked();
    }
}