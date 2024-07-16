package com.example.doctormaster.firebase;

import static com.example.doctormaster.utils.Utils.navigateToNextActivity;
import static com.example.doctormaster.utils.Utils.showToast;
import static com.example.doctormaster.utils.Utils.getErrorMessage;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public abstract class Authenticate {
    private static final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    // Login user with email and password
    public static void loginUser(@NonNull String email,
                                 @NonNull String password,
                                 @NonNull AppCompatActivity currentActivity,
                                 @NonNull Class<? extends AppCompatActivity> nextActivityClass) {

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(currentActivity, "Email and password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        navigateToNextActivity(currentActivity, nextActivityClass);
                    } else {
                        showToast(currentActivity, "Login Failed: " + getErrorMessage(task));
                    }
                });
    }

    // Register user with email and password
    public static void registerUser(@NonNull String email,
                                    @NonNull String password,
                                    @NonNull AppCompatActivity currentActivity,
                                    @NonNull Class<? extends AppCompatActivity> nextActivityClass) {

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(currentActivity, "Email and password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        navigateToNextActivity(currentActivity, nextActivityClass);
                    } else {
                        showToast(currentActivity, "Registration Failed: " + getErrorMessage(task));
                    }
                });
    }
}
