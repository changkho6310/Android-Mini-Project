package com.example.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class WifeFragment extends Fragment implements WifeCallback {

    private TextView mSalaryTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wife, container, false);
        mSalaryTextView = view.findViewById(R.id.txt_salary);
        return view;
    }

    @Override
    public void onReceiveSalary(int salary) {
        updateUI(salary);
    }

    private void updateUI(int salary) {
        String result = "Wife (Callback) received: " + String.valueOf(salary);
        mSalaryTextView.setText(result);
    }
}
