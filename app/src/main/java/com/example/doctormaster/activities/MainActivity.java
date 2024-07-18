package com.example.doctormaster.activities;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.doctormaster.utils.Utils.navigateToNextActivity;

import com.example.doctormaster.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginSignupButton = findViewById(R.id.loginSignupButton);

        loginSignupButton.setOnClickListener(v -> {
            navigateToNextActivity(MainActivity.this, LoginActivity.class);
        });
    }
}