package com.example.doctormaster;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.doctormaster.utils.Utils.navigateToNextActivity;

import com.example.doctormaster.firebase.FirestoreHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginSignupButton = findViewById(R.id.loginSignupButton);

        // Call the method to upload initial doctor data
        // FirestoreHelper.uploadInitialDoctors(this);

        loginSignupButton.setOnClickListener(v -> {
            navigateToNextActivity(MainActivity.this, LoginActivity.class);
        });
    }
}