package com.example.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class HusbandFragment extends Fragment {
    private EditText mSalaryEditText;
    private WifeCallback mWifeCallback;

    public void setWifeCallback(WifeCallback wifeCallback) {
        mWifeCallback = wifeCallback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_husband, container, false);
        mSalaryEditText = view.findViewById(R.id.edt_salary);
        setTextWatcher();
        return view;
    }

    private void setTextWatcher() {
        mSalaryEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    if (!charSequence.toString().isEmpty()) {
                        int salary = Integer.parseInt(mSalaryEditText.getText().toString());
                        callWifeAndGiveSalary(salary);
                    } else {
                        callWifeAndGiveSalary(0);
                    }
                } catch (NumberFormatException e) {
                    mSalaryEditText.setError("Lương nhiều quá, vợ không dám nhận :v");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void callWifeAndGiveSalary(int salary) {
        mWifeCallback.onReceiveSalary(salary);
    }
}
