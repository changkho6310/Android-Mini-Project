package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myapplication.databinding.ActivityMainBinding;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Way 1
        // ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Way 2
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Why we must use AtomicBoolean instead of booleam/Boolean ?
        // https://helpex.vn/question/khi-nao-toi-can-su-dung-atomicboolean-trong-java-60948b05f45eca37f4bfffb3
        // https://binhvova.wordpress.com/tag/atomicboolean/
        AtomicBoolean isError = new AtomicBoolean(false);

        binding.btnSum.setOnClickListener(view -> {
            try {
                int numA = Integer.parseInt(binding.edtNumA.getText().toString());
                int numB = Integer.parseInt(binding.edtNumB.getText().toString());
                binding.setSum(numA + " + " + numB + " = " + (numA + numB));
                isError.set(false);
            } catch (NumberFormatException e) {
                isError.set(true);
                binding.setSum("NumberFormatException!");
            }

            // Set Text Color
            if (isError.get()) {
                binding.tvResult.setTextColor(getResources().getColor(com.google.android.material.R.color.design_default_color_error));
            } else {
                binding.tvResult.setTextColor(getResources().getColor(com.google.android.material.R.color.m3_ref_palette_black));
            }
        });
    }
}