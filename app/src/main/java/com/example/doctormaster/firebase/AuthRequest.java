package com.example.doctormaster.firebase;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doctormaster.firebase.database.SecurePreferences;

public class AuthRequest {
    String email;
    String password;
    AppCompatActivity currentActivity;
    Class<? extends AppCompatActivity> nextActivity;
    SecurePreferences securePreferences;
    Boolean rememberMe;

    public AuthRequest(String email, String password, AppCompatActivity currentActivity, Class<? extends AppCompatActivity> nextActivity, SecurePreferences securePreferences, Boolean rememberMe) {
        this.email = email;
        this.password = password;
        this.currentActivity = currentActivity;
        this.nextActivity = nextActivity;
        this.securePreferences = securePreferences;
        this.rememberMe = rememberMe;
    }
}
