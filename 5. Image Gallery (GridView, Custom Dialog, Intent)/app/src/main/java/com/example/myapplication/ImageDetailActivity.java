package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageDetailActivity extends AppCompatActivity {
    private TextView tvImageContent;
    private ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        mappingView();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Image image = (Image) bundle.getSerializable("image");
            if (image != null) {
                tvImageContent.setText(image.getContent());
                ivImage.setImageResource(image.getImg());
            } else {
                // Handle if image == null => Show default image & text
                // ...
            }
        } else {
            // Handle if bundle == null => Show default image & text
            // ...
        }
    }

    private void mappingView() {
        tvImageContent = findViewById(R.id.image_detail_tv);
        ivImage = findViewById(R.id.image_detail_img);
    }
}