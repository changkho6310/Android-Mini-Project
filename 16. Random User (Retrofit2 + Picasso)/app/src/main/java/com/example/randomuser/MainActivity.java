package com.example.randomuser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.randomuser.api.ApiService;
import com.example.randomuser.model.ApiUser;
import com.squareup.picasso.Picasso;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView tvId;
    private TextView tvEmail;
    private TextView tvFirstName;
    private TextView tvLastName;
    private ImageView ivAvatar;
    private Button btnCallApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnCallApi.setOnClickListener(view -> clickCallApi());
    }

    private void clickCallApi() {
        Random rand = new Random();
        int randInt = rand.nextInt(10);

        ApiService.apiService.callUser(randInt).enqueue(new Callback<ApiUser>() {
            @Override
            public void onResponse(Call<ApiUser> call, Response<ApiUser> response) {
                Toast.makeText(MainActivity.this, "Call API Successfully", Toast.LENGTH_SHORT).show();

                ApiUser apiUser = response.body();
                if (apiUser != null) {
                    tvId.setText("ID: " + String.valueOf(apiUser.getData().getId()));
                    tvEmail.setText("Email: " + apiUser.getData().getEmail());
                    tvFirstName.setText("First Name: " + apiUser.getData().getFirst_name());
                    tvLastName.setText("Last Name: " + apiUser.getData().getLast_name());

                    Picasso.with(MainActivity.this).load(apiUser.getData().getAvatar()).into(ivAvatar);
                }
            }

            @Override
            public void onFailure(Call<ApiUser> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Call API Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addControls() {
        tvId = findViewById(R.id.tvId);
        tvEmail = findViewById(R.id.tvEmail);
        tvFirstName = findViewById(R.id.tvFirstName);
        tvLastName = findViewById(R.id.tvLastName);
        ivAvatar = findViewById(R.id.ivAvatar);
        btnCallApi = findViewById(R.id.btnCallApi);
    }
}