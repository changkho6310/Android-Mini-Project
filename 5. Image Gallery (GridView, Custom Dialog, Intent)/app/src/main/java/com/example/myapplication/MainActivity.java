package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Image> imageArray;
    private ImageAdapter imageAdapter;
    private GridView gvImageContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewMapping();
        fakeData();
        imageAdapter = new ImageAdapter(this, R.layout.image_item, imageArray);
        gvImageContainer.setAdapter(imageAdapter);
        gvImageContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showDialog(imageArray.get(i));
            }
        });
    }

    private void viewMapping() {
        gvImageContainer = findViewById(R.id.gv_container);
    }

    private void fakeData() {
        imageArray = new ArrayList<Image>();
        imageArray.add(new Image(R.drawable.dog, "Dog 1"));
        imageArray.add(new Image(R.drawable.dog, "Dog 2"));
        imageArray.add(new Image(R.drawable.dog, "Dog 3"));
        imageArray.add(new Image(R.drawable.dog, "Dog 4"));
        imageArray.add(new Image(R.drawable.dog, "Dog 5"));
        imageArray.add(new Image(R.drawable.dog, "Dog 6"));
        imageArray.add(new Image(R.drawable.dog, "Dog 7"));
        imageArray.add(new Image(R.drawable.dog, "Dog 8"));
        imageArray.add(new Image(R.drawable.dog, "Dog 9"));
        imageArray.add(new Image(R.drawable.dog, "Dog 10"));
        imageArray.add(new Image(R.drawable.dog, "Dog 11"));
        imageArray.add(new Image(R.drawable.dog, "Dog 12"));
        imageArray.add(new Image(R.drawable.dog, "Dog 13"));
        imageArray.add(new Image(R.drawable.dog, "Dog 14"));
        imageArray.add(new Image(R.drawable.dog, "Dog 15"));
        imageArray.add(new Image(R.drawable.dog, "Dog 16"));
        imageArray.add(new Image(R.drawable.dog, "Dog 17"));
        imageArray.add(new Image(R.drawable.dog, "Dog 18"));
        imageArray.add(new Image(R.drawable.dog, "Dog 19"));
        imageArray.add(new Image(R.drawable.dog, "Dog 20"));
    }

    private void showDialog(Image image) {
        Dialog dialog = new Dialog(this);

        // Remove header/title (Some device won't support header/title)
        // Note : Must call requestWindowFeature() before setContentView()
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.image_dialog);

        // User click outside dialog won't dismiss dialog
        dialog.setCanceledOnTouchOutside(false);

        ImageView imageDialog = dialog.findViewById(R.id.image_dialog_img);
        Button btnDetail = dialog.findViewById(R.id.btn_detail);
        Button btnClose = dialog.findViewById(R.id.btn_close);
        imageDialog.setImageResource(image.getImg());

        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ImageDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("image", image);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();

                dialog.dismiss();
            }
        });

        dialog.show();
    }
}