package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText edtCalendar, edtTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewMapping();
        edtCalendar.setOnClickListener(view -> {
            selectDate();
        });
        edtTime.setOnClickListener(view -> selectTime());
    }

    private void viewMapping() {
        edtCalendar = findViewById(R.id.edt_calendar);
        edtTime = findViewById(R.id.edt_time);
    }

    private void selectDate() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, i, i1, i2) -> {
            // i : year
            // i1 : month
            // i2 : day
            calendar.set(i, i1, i2);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            edtCalendar.setText(simpleDateFormat.format(calendar.getTime()));
        }, year, month, day);

        datePickerDialog.show();
    }

    private void selectTime() {
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                // i : hours
                // i1 : minutes
                // Seconds calendar tự tính (get your seconds)
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                calendar.set(0, 0, 0, i, i1);
                edtTime.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, hours, minutes, true);

        timePickerDialog.show();
    }
}