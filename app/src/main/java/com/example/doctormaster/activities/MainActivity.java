package com.example.doctormaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doctormaster.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginPatient = findViewById(R.id.patientLoginButton);
        Button loginDoctor = findViewById(R.id.doctorLoginButton);

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);

        // FirestoreHelper.uploadInitialDoctors(this);

        loginPatient.setOnClickListener(v -> {
            intent.putExtra("USER_TYPE", "patient");
            startActivity(intent);
        });

        loginDoctor.setOnClickListener(v -> {
            intent.putExtra("USER_TYPE", "doctor");
            startActivity(intent);
        });
    }
}