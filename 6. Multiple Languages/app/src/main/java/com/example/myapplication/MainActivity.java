package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tvFullName, tvAddress;
    EditText edtFullName, edtAddress;
    Button btnClear, btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mappingView();

        btnClear.setOnClickListener(view -> {
            edtFullName.setText("");
            edtAddress.setText("");
        });

        btnSubmit.setOnClickListener(view -> {
            String str = getResources().getString(R.string.text_hello) + " " + edtFullName.getText();
            tvFullName.setText(str);
            str = getResources().getString(R.string.text_your_address_is) + ": " + edtAddress.getText();
            tvAddress.setText(str);
        });
    }

    private void mappingView() {
        tvFullName = findViewById(R.id.tv_fullname);
        tvAddress = findViewById(R.id.tv_address);
        edtFullName = findViewById(R.id.edt_fullname);
        edtAddress = findViewById(R.id.edt_address);
        btnClear = findViewById(R.id.btn_clear);
        btnSubmit = findViewById(R.id.btn_submit);

    }
}