package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> strArr;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewMapping();
        fakeData();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, strArr);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                confirmDeleteItem(i);
            }
        });
    }

    private void fakeData() {
        strArr = new ArrayList<String>();
        strArr.add("Grade 1");
        strArr.add("Grade 2");
        strArr.add("Grade 3");
        strArr.add("Grade 4");
        strArr.add("Grade 5");
        strArr.add("Grade 6");
        strArr.add("Grade 7");
        strArr.add("Grade 8");
        strArr.add("Grade 9");
        strArr.add("Grade 10");
        strArr.add("Grade 11");
        strArr.add("Grade 12");
    }

    private void viewMapping() {
        listView = findViewById(R.id.lv);
    }

    private void confirmDeleteItem(int pos) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getResources().getString(R.string.warning_delete_item));
        alertDialog.setIcon(R.drawable.ic_delete);
        String message = getResources().getString(R.string.confirm_message_delete_item);
        message += " \"" + strArr.get(pos) + "\" ?";
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton(getResources().getString(R.string.confirm_yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                strArr.remove(pos);
                arrayAdapter.notifyDataSetChanged();
            }
        });
        alertDialog.setNegativeButton(getResources().getString(R.string.confirm_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alertDialog.show();
    }
}