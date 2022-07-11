package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.myapplication.adapter.UserAdapter;

import java.util.ArrayList;

public class UserListFragment extends Fragment implements
        UserAdapter.OnButtonClickListener,
        DeleteFragment.OnDeleteListener,
        UpdateFragment.OnUpdateListener {
    private static final String USER_ARRAYLIST = "userArrayList";
    private ArrayList<String> userList;
    private UserAdapter adapter;
    private int idxClicked;

    public UserListFragment() {
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(USER_ARRAYLIST, userList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, null);
        if (savedInstanceState == null) {
            userList = new ArrayList<>();
            userList.add("Hello 0");
            userList.add("Hello 1");
            userList.add("Hello 2");
            userList.add("Hello 3");
        } else {
            userList = savedInstanceState.getStringArrayList(USER_ARRAYLIST);
        }
        adapter = new UserAdapter(this, userList);
        ListView lvUsers = view.findViewById(R.id.lvUser);
        lvUsers.setAdapter(adapter);
        return view;
    }

    public void addUser(String name) {
        if (!TextUtils.isEmpty(name)) {
            userList.add(name);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDelete(int i) {
        idxClicked = i;
        new DeleteFragment().show(getChildFragmentManager(), DeleteFragment.TAG);
    }

    @Override
    public void onUpdate(int i) {
        idxClicked = i;
        String name = userList.get(i);

        // Để khi click vào edit => Mở edit dialog fragment lên but name ở đâu ra mà set?
        // Vậy nên pass name từ Fragment cha vào Edit DialogFragment để set EditText
        UpdateFragment updateFragment = UpdateFragment.newInstance(name);
        updateFragment.show(getChildFragmentManager(), UpdateFragment.TAG);
    }


    @Override
    public void onDeleteResult(boolean ok) {
        if (ok) {
            userList.remove(idxClicked);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onUpdateResult(boolean ok, String newName) {
        if (ok) {
            userList.set(idxClicked, newName);
            adapter.notifyDataSetChanged();
        }
    }
}