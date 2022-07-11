package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.util.Log;

public class DeleteFragment extends DialogFragment {
    public static final String TAG = "ConfirmDelete";
    private OnDeleteListener iOnDeleteListener;

    public interface OnDeleteListener {
        void onDeleteResult(boolean ok);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        try {
            iOnDeleteListener = (OnDeleteListener) getParentFragment();
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getParentFragment().toString()
                    + " must implement NoticeDialogListener");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage(getString(R.string.confirm_delete_msg))
                .setTitle(getString(R.string.confirm_delete_title))
                .setPositiveButton("OK", (dialogInterface, i) -> iOnDeleteListener.onDeleteResult(true))
                .setNegativeButton("Cancel", (dialogInterface, i) -> iOnDeleteListener.onDeleteResult(false)).create();

        return builder.create();
    }
}