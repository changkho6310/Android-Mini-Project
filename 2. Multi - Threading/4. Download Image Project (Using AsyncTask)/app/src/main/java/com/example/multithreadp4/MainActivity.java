package com.example.multithreadp4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private ImageView ivImageContainer;
    private EditText edtImageLink;
    private Button btnDownloadImage;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnDownloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDownloadImage();
            }
        });
    }

    private void addControls() {
        edtImageLink = findViewById(R.id.edt_image_link);
        ivImageContainer = findViewById(R.id.iv_image_container);
        btnDownloadImage = findViewById(R.id.btn_download_image);
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setTitle("Notification");
        dialog.setMessage("Downloading, pls wait!");
    }

    private void handleDownloadImage() {
        ImageTask imageTask = new ImageTask();
        String imageLink = edtImageLink.getText().toString();
        if(imageLink.equals("")){
            return;
        }
        imageTask.execute(imageLink);
    }

    class ImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            dialog.dismiss();
            ivImageContainer.setImageBitmap(bitmap);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                return bitmap;
            } catch (Exception ex) {
                Log.e("AAA", ex.toString());
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}