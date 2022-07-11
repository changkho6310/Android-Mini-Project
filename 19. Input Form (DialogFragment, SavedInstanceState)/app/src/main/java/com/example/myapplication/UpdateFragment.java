package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class UpdateFragment extends DialogFragment {
    public static final String TAG = "ConfirmUpdate";
    public static final String OLD_NAME = "OldName";
    private OnUpdateListener listener;
    private String oldName;

    public interface OnUpdateListener {
        void onUpdateResult(boolean ok, String newName);
    }

    public static UpdateFragment newInstance(String oldName) {
        Bundle args = new Bundle();
        args.putString(OLD_NAME, oldName);
        UpdateFragment fragment = new UpdateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            oldName = bundle.getString(OLD_NAME);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        try {
            listener = (OnUpdateListener) getParentFragment();
        } catch (ClassCastException e) {
            Fragment parentFragment = getParentFragment();
            if (parentFragment != null) {
                throw new ClassCastException(parentFragment
                        + " must implement NoticeDialogListener");
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update, null);
        EditText edtName = view.findViewById(R.id.edtUpdate);
        if (edtName == null) {
            throw new RuntimeException("EditText edtName is null");
        }
        edtName.setText(oldName);
        builder.setView(view)
                .setTitle("Edit name")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = edtName.getText().toString().trim();
                        if (!TextUtils.isEmpty(name)) {
                            listener.onUpdateResult(true, name);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
    }
}