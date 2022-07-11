package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {
    private ArrayList<String> userList;
    LayoutInflater inflater;
    private OnButtonClickListener iOnButtonClickListener;

    public interface OnButtonClickListener {
        void onDelete(int i);

        void onUpdate(int i);
    }

    public UserAdapter(Fragment fragment, ArrayList<String> userList) {
        if (fragment instanceof UserAdapter.OnButtonClickListener) {
            iOnButtonClickListener = (OnButtonClickListener) fragment;
        } else {
            throw new RuntimeException(fragment.toString() + " must implement UserAdapter.OnButtonClickListener");
        }
        this.userList = userList;
        this.inflater = LayoutInflater.from(fragment.getActivity());
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.item_user, null);
            viewHolder.txtName = view.findViewById(R.id.txtName);
            viewHolder.btnDelete = view.findViewById(R.id.btnDelete);
            viewHolder.btnEdit = view.findViewById(R.id.btnEdit);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.txtName.setText(userList.get(i));
        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOnButtonClickListener.onUpdate(i);
            }
        });

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOnButtonClickListener.onDelete(i);
            }
        });
        return view;
    }

    private class ViewHolder {
        public TextView txtName;
        public Button btnEdit;
        public Button btnDelete;
        public ViewHolder() {
        }
    }
}
