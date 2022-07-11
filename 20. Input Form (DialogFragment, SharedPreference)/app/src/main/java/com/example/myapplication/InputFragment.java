package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.datalocal.DataLocalManager;

public class InputFragment extends Fragment {
    private static String NAME = "name";

    public interface OnButtonClicked {
        void onClick(String name);
    }

    private OnButtonClicked iOnButtonClicked;
    private EditText edtName;
    private Button btnSubmit;
    private String name;

    public InputFragment() {
    }

    @Override
    public void onPause() {
        super.onPause();
        name = edtName.getText().toString().trim();
        if (!TextUtils.isEmpty(name)) {
            DataLocalManager.setName(name);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof InputFragment.OnButtonClicked) {
            iOnButtonClicked = (OnButtonClicked) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement InputInformationFragment.OnButtonClicked.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input, null);

        edtName = view.findViewById(R.id.edtInputName);
        btnSubmit = view.findViewById(R.id.btnSubmitInput);

        // If first time installed app => DataLocalManager.getName() return ""
        edtName.setText(DataLocalManager.getName());

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edtName.getText().toString().trim();
                if (!TextUtils.isEmpty(name)) {
                    edtName.setText("");
                    iOnButtonClicked.onClick(name);
                }
            }
        });
        return view;
    }
}