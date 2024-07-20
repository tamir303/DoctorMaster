package com.example.doctormaster.firebase;

import static com.example.doctormaster.utils.Utils.navigateToNextActivity;
import static com.example.doctormaster.utils.Utils.showToast;
import static com.example.doctormaster.utils.Utils.getErrorMessage;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;

public abstract class Authenticate {
    private static final FirebaseAuth mAuth = FirebaseOperations.getAuth();

    // Login user with email and password
    public static void loginUser(@NonNull AuthRequest authRequest) {

        if (authRequest.email.isEmpty() || authRequest.password.isEmpty()) {
            Toast.makeText(authRequest.currentActivity, "Email and password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(authRequest.email, authRequest.password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (authRequest.rememberMe)
                            authRequest.securePreferences.saveCredentials(authRequest.email, authRequest.password);
                        else
                            authRequest.securePreferences.clearCredentials();
                        navigateToNextActivity(authRequest.currentActivity, authRequest.nextActivity);
                    } else {
                        showToast(authRequest.currentActivity, "Login Failed: " + getErrorMessage(task));
                    }
                });
    }

    // Register user with email and password
    public static void registerUser(@NonNull AuthRequest authRequest) {

        if (authRequest.email.isEmpty() || authRequest.password.isEmpty()) {
            Toast.makeText(authRequest.currentActivity, "Email and password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(authRequest.email, authRequest.password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (authRequest.rememberMe)
                            authRequest.securePreferences.saveCredentials(authRequest.email, authRequest.password);
                        else
                            authRequest.securePreferences.clearCredentials();
                        navigateToNextActivity(authRequest.currentActivity, authRequest.nextActivity);
                    } else {
                        showToast(authRequest.currentActivity, "Registration Failed: " + getErrorMessage(task));
                    }
                });
    }
}
