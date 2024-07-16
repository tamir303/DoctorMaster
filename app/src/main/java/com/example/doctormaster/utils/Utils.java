package com.example.doctormaster.utils;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public abstract class Utils {

    // Navigate to the next activity
    public static void navigateToNextActivity(AppCompatActivity currentActivity,
                                               Class<? extends AppCompatActivity> nextActivityClass) {
        Intent intent = new Intent(currentActivity, nextActivityClass);
        currentActivity.startActivity(intent);
        currentActivity.finish();
    }

    // Show a toast message
    public static void showToast(AppCompatActivity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    // Get error message from task
    public static String getErrorMessage(@NonNull com.google.android.gms.tasks.Task<?> task) {
        return Objects.requireNonNull(task.getException()).getMessage();
    }
}
